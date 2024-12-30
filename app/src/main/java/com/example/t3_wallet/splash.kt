package com.example.t3_wallet

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3_wallet.databinding.ActivitySplashBinding

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logoImage = findViewById<ImageView>(R.id.imageView)
        val getStarted = findViewById<Button>(R.id.button)
        val animatedDrawable = logoImage.drawable as AnimatedVectorDrawable
        animatedDrawable.start()
        binding.button.setOnClickListener {
            animatedDrawable.stop()
            val intent=Intent(this,walletSetupActivity2::class.java)
            startActivity(intent)

        }
    }
}
