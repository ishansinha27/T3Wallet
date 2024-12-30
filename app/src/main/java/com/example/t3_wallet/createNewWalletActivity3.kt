package com.example.t3_wallet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.t3_wallet.databinding.ActivityCreateNewWallet3Binding
import com.example.t3_wallet.retrofit.ApiInterface
import com.example.t3_wallet.retrofit.PostModel
import com.example.t3_wallet.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class createNewWalletActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCreateNewWallet3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDatabase(this@createNewWalletActivity3)
        val userDao = db.userDataDao()

        binding.createnewwalletbuttonn.setOnClickListener {

            val username = "user_${Random.nextInt(1000)}"
            val password = binding.editTextcopy.text.toString()
            val postModel = PostModel(username = username, password = password)

            RetrofitClient.apiInterface.postData(postModel).enqueue(object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@createNewWalletActivity3, "Data sent successfully!", Toast.LENGTH_SHORT).show()
                        Log.d("Response", "Response: hello${response.body()}")

                        val responseBody = response.body()

                        if (responseBody != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                saveDataToDatabase(responseBody, userDao)
                            }
                            val ethWalletAddress = responseBody.eth?.getOrNull(0)?.wallet ?: ""
                            val seedPhrase = responseBody.seedPhrase!!

                            val solanaWalletAddress = responseBody.solana?.getOrNull(0)?.wallet ?: ""
                            val soidWalletAddress = responseBody.soid?.getOrNull(0)?.wallet ?: ""
                            val btcWalletAddress = responseBody.btc?.getOrNull(0)?.wallet ?: ""


                            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()



                            editor.putString("ETH_WALLET", ethWalletAddress)
                            editor.putString("SOLANA_WALLET", solanaWalletAddress)
                            editor.putString("seedPhrase", seedPhrase)
                            editor.putString("SOID_WALLET", soidWalletAddress)
                            editor.putString("BTC_WALLET", btcWalletAddress)


                            editor.apply()






                        } else {
                            Log.e("Error", "Empty response body")
                        }
                    } else {
                        Toast.makeText(this@createNewWalletActivity3, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    Toast.makeText(this@createNewWalletActivity3, "Error here: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Error", "Failure: ${t.message}")
                }
            })
        }
    }

    suspend fun saveDataToDatabase(postModel: PostModel, userDataDao: UseraDataDAO) {
        val userData = UserData(
            username = postModel.username ?: "default_username",
            password = postModel.password ?: "",
            seedPhrase = postModel.seedPhrase ?: ""
        )

        userDataDao.insertUser(userData)

        val walletTypes = listOf("ETH", "SOLANA", "SOID", "BTC")
        val walletDataList = mutableListOf<WalletData>()

        for (type in walletTypes) {
            val walletList = when (type) {
                "ETH" -> postModel.eth
                "SOLANA" -> postModel.solana
                "SOID" -> postModel.soid
                "BTC" -> postModel.btc
                else -> null
            }

            walletList?.forEach { wallet ->
                walletDataList.add(
                    WalletData(
                        walletAddress = wallet.wallet ?: "",
                        privateKey = wallet.privateKey ?: "",
                        type = type,
                        userId = userData.username
                    )
                )
            }
        }

        if (walletDataList.isNotEmpty()) {
            userDataDao.insertWallets(walletDataList)
        }

        val soidWallets = walletDataList.filter { it.type == "SOID" }
        soidWallets.forEach { wallet ->
            val walletAddress = wallet.walletAddress

            callSOIDapi(walletAddress)
        }
    }

    private fun callSOIDapi(walletAddress: String) {
        val url = "https://explorer-restapi.sovereignty.one/identity/identity/id/$walletAddress"
        val queue = Volley.newRequestQueue(this@createNewWalletActivity3)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("Response APIII ", response.toString())

                val idObject = response.optJSONObject("id")
                Log.d("idobjecttt", idObject.toString())
                val did = idObject?.optString("did")
                Log.d("did hellloo", did.toString())

                if (did != null) {
                    val sharedPreferences = this.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("did", did)
                    editor.apply()

                    val intent = Intent(this@createNewWalletActivity3, HomeActivity::class.java)
                    intent.putExtra("did", did)
                    startActivity(intent)
                }
            },
            { error ->
                error.printStackTrace()
                println("Error: ${error.message}")
            }
        )

        queue.add(jsonObjectRequest)
    }



}
