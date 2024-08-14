package com.habibfr.suitmedia_test_mobile.view.second

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.habibfr.suitmedia_test_mobile.databinding.ActivitySecondBinding
import com.habibfr.suitmedia_test_mobile.view.factory.ViewModelFactory
import com.habibfr.suitmedia_test_mobile.view.third.ThirdActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val sharedViewModel: SharedViewModel by viewModels {
        ViewModelFactory.getInstance(this@SecondActivity)
    }

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

            sharedViewModel.userData.observe(this@SecondActivity, Observer { userData ->
                if (userData != null) {
                    if (userData.name != "") {
                        txtUser.text = userData.name
                    }
                    if (userData.selectedUser != "") {
                        txtSelectedUsername.text = "Selected ${userData.selectedUser}"
                    }
                }
            })

            btnNext.setOnClickListener {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}