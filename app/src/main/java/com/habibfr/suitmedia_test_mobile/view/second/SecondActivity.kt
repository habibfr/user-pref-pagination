package com.habibfr.suitmedia_test_mobile.view.second

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.habibfr.suitmedia_test_mobile.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding) {

            setSupportActionBar(topAppBar)
            topAppBar.setNavigationOnClickListener {
                onBackPressed()
            }

            val username = intent.getStringExtra("username")
            if (username != null) {
                txtUser.text = username
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}