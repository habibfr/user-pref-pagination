package com.habibfr.suitmedia_test_mobile.view.first

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.habibfr.suitmedia_test_mobile.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            txtResult.visibility = View.GONE
            btnCheck.setOnClickListener {
                val textCheck = etPalindrome.text.toString().trim()

                if (textCheck.isEmpty()) {
                    txtResult.text = "Input masih kosong!"
                    txtResult.setTextColor(Color.RED)
                    txtResult.visibility = View.VISIBLE
                } else {
                    val isPalindrome = isPalindrome(textCheck)

                    txtResult.text =
                        if (isPalindrome) "$textCheck isPalindrome" else "$textCheck not palindrome"

                    val color = if (isPalindrome) {
                        Color.GREEN
                    } else {
                        Color.RED
                    }

                    txtResult.setTextColor(color)
                    txtResult.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.lowercase().replace(Regex("[^a-z0-9]"), "")
        return cleanText == cleanText.reversed()
    }
}