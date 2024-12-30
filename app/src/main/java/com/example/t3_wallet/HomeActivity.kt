package com.example.t3_wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.t3_wallet.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the did from the intent
        val did = intent.getStringExtra("did")

        // Only load the HomeFragment with arguments on the initial launch
        if (savedInstanceState == null) {
            val fragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("did", did)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit()
        }

        binding.bottomnavigtion.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment(), did)
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
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
