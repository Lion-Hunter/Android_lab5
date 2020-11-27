package com.example.lab5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lab5.databinding.Task22Binding

const val TO_THIRD = 1

class Second: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task22Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.to1Button.setOnClickListener { finish() }

        binding.to3Button.setOnClickListener {
            startActivityForResult(Intent(this, Third::class.java), TO_THIRD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == TO_THIRD && resultCode == RESULT_OK
                && data.getIntExtra("TO_FIRST", 0) == 1) { finish() }
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
