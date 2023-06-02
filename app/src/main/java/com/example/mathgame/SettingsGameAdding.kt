package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.system.exitProcess


class SettingsGameAdding : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var addingGameSettingsRounds: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var addingGameSettingsRange: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_game_adding)

        addingGameSettingsRounds = findViewById<TextView>(R.id.et_adding_settings_rounds)
        addingGameSettingsRange = findViewById<TextView>(R.id.et_adding_settings_range)

        val btnPlay : Button = findViewById(R.id.btn_adding_game_settings_play)
        btnPlay.setOnClickListener {

            if(addingGameSettingsRounds.text.isEmpty()) {
                addingGameSettingsRounds.error = getString(
                    R.string.adding_game_settings_missing_rounds_message)
            }else if (addingGameSettingsRounds.text.toString().toInt() <= 0){
                addingGameSettingsRounds.error = getString(
                    R.string.adding_game_settings_incorrect_rounds_message)
            }else if (addingGameSettingsRange.text.isEmpty()) {
                addingGameSettingsRange.error = getString(
                    R.string.adding_game_settings_missing_range_message)
            }else if (addingGameSettingsRange.text.toString().toInt() < 10){
                addingGameSettingsRange.error = getString(
                    R.string.adding_game_settings_incorrect_range_message)
            }else{
                val intent = Intent(this, GameAdding::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
