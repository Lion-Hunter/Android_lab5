package com.example.lab5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lab5.databinding.Task23Binding

class Third: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task23Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.to2From3.setOnClickListener {
            finish()
        }

        binding.to1From3.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("TO_FIRST", 1))
            finish()
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