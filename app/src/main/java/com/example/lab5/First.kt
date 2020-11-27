package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.Task21Binding

class First : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task21Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.to2Button.setOnClickListener {
            startActivity(Intent(this, Second::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.toAbout) {
            startActivity(Intent(this, About::class.java))
        }

        return true
    }
}