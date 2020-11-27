package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.button.text = getString(R.string.button_pressed)
        }
    }
}