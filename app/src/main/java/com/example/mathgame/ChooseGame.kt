package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ChooseGame : AppCompatActivity() {

    companion object{
        lateinit var source: String
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_game)

        findViewById<TextView>(R.id.welcome_user_text_view).text = getString(
            R.string.choose_game_screen_header, MainActivity.etUserName.text)

        val btnAdding : Button = findViewById(R.id.btn_adding)
        btnAdding.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            source = getString(R.string.game_settings_screen_source_adding)
        }

        val btnSubtracting : Button = findViewById(R.id.btn_subtracting)
        btnSubtracting.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            source = getString(R.string.game_settings_screen_source_subtracting)
        }

        val btnMultiplying : Button = findViewById(R.id.btn_multiplying)
        btnMultiplying.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            source = getString(R.string.game_settings_screen_source_multiplying)
        }

        val btnDividing : Button = findViewById(R.id.btn_dividing)
        btnDividing.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            source = getString(R.string.game_settings_screen_source_dividing)
        }
    }
}
