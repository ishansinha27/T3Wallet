package com.example.t3_wallet

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.t3_wallet.databinding.ActivityCryptoDetailBinding

class CryptoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityCryptoDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)





        val expandbutton=findViewById<ImageButton>(R.id.expandbutton)
        val cryptoname=intent.getStringExtra("crypto_name")
        val cryptoprice=intent.getStringExtra("crypto_price")
        val crypto=intent.getIntExtra("crypto_image",R.drawable.maskbg)
        binding.cryptoname.text=cryptoname










        binding.cryptoprice.text=cryptoprice
        binding.cryptoimage.setImageResource(crypto)
        expandbutton.setOnClickListener {
            val bottomSheet = CustomBottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "CustomBottomSheet")
        }
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)


        val ethWalletAddress = sharedPreferences.getString("ETH_WALLET", "")
        val solanaWalletAddress = sharedPreferences.getString("SOLANA_WALLET", "")
        val soidWalletAddress = sharedPreferences.getString("SOID_WALLET", "")
        val btcWalletAddress = sharedPreferences.getString("BTC_WALLET", "")
        Log.d("eth hellpppp",ethWalletAddress.toString())
        if(cryptoname=="SOID"){
            binding.cryptowalletaddress.text=soidWalletAddress.toString()
        }
        if(cryptoname=="ETH"){
            binding.cryptowalletaddress.text=ethWalletAddress.toString()
        }
        if(cryptoname=="Solana"){
            binding.cryptowalletaddress.text=solanaWalletAddress.toString()
        }
        if(cryptoname=="BTC"){
            binding.cryptowalletaddress.text=btcWalletAddress.toString()
        }

        val currwalletaddress=findViewById<TextView>(R.id.cryptowalletaddress)
        val currwalletaddresss= currwalletaddress.text.toString()
        Log.d("eth hellpppppp",currwalletaddresss)
        val sharedPreference = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()



        editor.putString("cryptoname", cryptoname)

        editor.putString("currentwalletaddress",currwalletaddresss)
        editor.apply()





        binding.bottomnavigtionn.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.history -> replaceFragment(HistoryFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                else -> { }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, did: String? = null) {
        // Pass "did" to HomeFragment only
        if (fragment is HomeFragment && did != null) {
            fragment.arguments = Bundle().apply {
                putString("did", did)
            }
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }


    }
