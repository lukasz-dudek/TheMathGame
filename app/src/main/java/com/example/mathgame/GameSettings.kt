package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class GameSettings : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var GameRounds: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var GameRange: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        GameRounds = findViewById<TextView>(R.id.et_game_settings_rounds_value)
        GameRange = findViewById<TextView>(R.id.et_game_settings_range_value)
        findViewById<TextView>(R.id.et_game_settings_screen_source).text = ChooseGame.Source

        val btnPlay : Button = findViewById(R.id.btn_game_settings_play)
        btnPlay.setOnClickListener {

            if(GameRounds.text.isEmpty()) {
                GameRounds.error = getString(
                    R.string.game_settings_screen_missing_rounds_error_message)
            }else if (GameRounds.text.toString().toInt() <= 0){
                GameRounds.error = getString(
                    R.string.game_settings_screen_incorrect_rounds_error_message)
            }else if (GameRange.text.isEmpty()) {
                GameRange.error = getString(
                    R.string.game_settings_screen_missing_range_error_message)
            }else if (GameRange.text.toString().toInt() < 10){
                GameRange.error = getString(
                    R.string.game_settings_screen_incorrect_range_error_message)
            }else{
                val intent = Intent(this, GameAdding::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
