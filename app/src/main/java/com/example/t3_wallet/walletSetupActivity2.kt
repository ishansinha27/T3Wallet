package com.example.t3_wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3_wallet.databinding.ActivityWalletSetup2Binding

class walletSetupActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityWalletSetup2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.createnewwalletbutton.setOnClickListener {
            val i= Intent(this,createNewWalletActivity3::class.java)
            startActivity(i)

        }
        binding.getstartedbutton.setOnClickListener {
            val f=Intent(this,SignInActivity3::class.java)
            startActivity(f)
        }

    }
}


