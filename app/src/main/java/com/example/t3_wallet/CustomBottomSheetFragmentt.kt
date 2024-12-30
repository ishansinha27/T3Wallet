package com.example.t3_wallet

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONObject

class CustomBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your BottomSheet layout
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val lineButton: View = view.findViewById(R.id.lineButton)
        val account1textview=view.findViewById<TextView>(R.id.textView20)
        val account2textview=view.findViewById<TextView>(R.id.textView21)
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val sharedPreference = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val cryptoname = sharedPreference.getString("cryptoname", "No Crypto Name Found")
        val currentwalletaddress = sharedPreference.getString("currentwalletaddress", "No Crypto Name Found")
        Log.d("currentwalletaddress hellll", "$currentwalletaddress")
        account1textview.text=" Account 1 : $currentwalletaddress"
        Log.d("cryptoname hellll", "$cryptoname")

        val seedPhrase = sharedPreferences.getString("seedPhrase", "No Seed Phrase Found")

        val createnewbutton = view.findViewById<Button>(R.id.button2)
        createnewbutton.setOnClickListener {
            Log.d("button", "button clicked")
            val requestData = JSONObject().apply {
                put(
                    "seedPhrase",
                    "$seedPhrase"
                )
                put("chain", "$cryptoname")
                put("addressIndex", 2)
            }

            // API endpoint
            val url = "https://t3backend.onrender.com/createWallet/seeds"

            // Initialize Volley request queue
            val requestQueue = Volley.newRequestQueue(requireContext())

            // Create a POST request
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                url,
                requestData,
                { response ->

                    // Parse the response to get the "wallet" field
                    val wallet = response.optString("wallet", "No wallet found")
                    Log.d("API_RESPONSE helllllllpp", "Wallet: $wallet")
                    account2textview.text="Account 2 : $wallet"
                },
                { error ->
                    // Handle errors
                    Log.e("API_ERROR", "Error: ${error.message}")
                }
            )

            // Add the request to the queue
            requestQueue.add(jsonObjectRequest)
        }



    lineButton.setOnClickListener{
        dismiss()
    }



}}