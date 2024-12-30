package com.example.t3_wallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.t3_wallet.createNewWalletActivity3
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var requestQueue: com.android.volley.RequestQueue
    private val bitcoinurl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"
    private val ethereumurl = "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd"
    private val solanaurl = "https://api.coingecko.com/api/v3/simple/price?ids=solana&vs_currencies=usd"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val hostActivityName = activity?.javaClass?.simpleName
        Log.d("FragmentHost", "Hosted by: $hostActivityName")
        val didTextView = view.findViewById<TextView>(R.id.didTextView)
        val didFromIntent = arguments?.getString("did")
        didTextView.text = " $didFromIntent"







        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.crypto)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val initialCryptoList = mutableListOf(
            CryptoItem("SOID", "$ 0.15", "+ 0.0%", R.drawable.maskbg, bitcoinurl),
            CryptoItem("BTC", "$ Loading...", "+ 0.0%", R.drawable.bitcoinlogocc, bitcoinurl),
            CryptoItem("ETH", "$ Loading...", "+ 0.0%", R.drawable.eth, ethereumurl),
            CryptoItem("Solana", "$ Loading...", "+ 0.0%", R.drawable.solana, solanaurl)
        )

        cryptoAdapter = CryptoAdapter(initialCryptoList) { cryptoItem ->
            // Start new activity with crypto item details
            val intent = Intent(activity, CryptoDetailActivity::class.java).apply {
                putExtra("crypto_name", cryptoItem.cryptoName)
                putExtra("crypto_price", cryptoItem.cryptoPrice)
                putExtra("crypto_image", cryptoItem.cryptoImage)
            }
            startActivity(intent)
        }

        recyclerView.adapter = cryptoAdapter

        // Initialize Volley Request Queue
        requestQueue = Volley.newRequestQueue(requireContext())

        // Fetch Data for each cryptocurrency
        for (cryptoItem in initialCryptoList) {
            fetchData(cryptoItem)
        }

        return view
    }

    private fun fetchData(cryptoItem: CryptoItem) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, cryptoItem.apiUrl, null,
            { response ->
                // Fetch the USD price for the specific cryptocurrency
                val price = when (cryptoItem.cryptoName.toLowerCase()) {
                    "btc" -> response.optJSONObject("bitcoin")?.optString("usd", "Price not available")
                    "eth" -> response.optJSONObject("ethereum")?.optString("usd", "Price not available")
                    "solana" -> response.optJSONObject("solana")?.optString("usd", "Price not available")
                    else -> "Price not available"
                }

                // Check if the price is found
                if (price != "Price not available") {
                    cryptoItem.cryptoPrice = "$$price"  // Format the price with "$"
                    cryptoAdapter.notifyDataSetChanged()  // Notify the adapter
                } else {
                    cryptoItem.cryptoPrice = "0.025"
                    cryptoAdapter.notifyDataSetChanged()  // Notify the adapter
                }
            },
            { error ->
                error.printStackTrace()  // Handle error
            }
        )

        requestQueue.add(jsonObjectRequest)  // Add request to the queue
    }


}
