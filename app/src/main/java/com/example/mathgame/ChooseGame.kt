package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlin.system.exitProcess

class ChooseGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_game)

        val btnAdding : Button = findViewById(R.id.btn_adding)
        btnAdding.setOnClickListener {
            val intent = Intent(this, SettingsGameAdding::class.java)
            startActivity(intent)
        }

        val btnSubtracting : Button = findViewById(R.id.btn_subtracting)
        btnSubtracting.setOnClickListener {
            val intent = Intent(this, GameSubtracting::class.java)
            startActivity(intent)
        }

        val btnMultiplying : Button = findViewById(R.id.btn_multiplying)
        btnMultiplying.setOnClickListener {
            val intent = Intent(this, GameMultiplying::class.java)
            startActivity(intent)
        }

        val btnDividing : Button = findViewById(R.id.btn_dividing)
        btnDividing.setOnClickListener {
            val intent = Intent(this, GameDividing::class.java)
            startActivity(intent)
        }
    }
}
