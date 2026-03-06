package com.trabalhos.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ModernArtActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modern_art)

        supportActionBar?.title = "Modern Art UI"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_modern_art, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_more_info -> {
                showMoreInfoDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMoreInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("More Information")
            .setMessage("Inspired by the works of Piet Mondrian.\n\nVisit the MoMA to learn more.")
            .setPositiveButton("Visit MoMA") { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.moma.org"))
                startActivity(intent)
            }
            .setNegativeButton("Not Now", null)
            .show()
    }
}
