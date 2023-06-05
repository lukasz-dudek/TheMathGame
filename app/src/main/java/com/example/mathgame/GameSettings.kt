package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView


class GameSettings : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var GameRounds: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var GameRange: TextView

        var SubtractingToggleButtonSetting: Boolean = false
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

        if (ChooseGame.Source != getString(R.string.game_settings_screen_source_subtracting)) {
            val toggleCard: CardView = findViewById(R.id.game_settings_toggle_card)
            toggleCard.visibility = View.GONE
        }

        val toggle: SwitchCompat = findViewById(R.id.tg_game_settings_subtraction_toggle)
        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, R.string.game_settings_subtraction_toggle_setting_on,
                    Toast.LENGTH_SHORT).show()
                SubtractingToggleButtonSetting = true
            }else{
                Toast.makeText(this, R.string.game_settings_subtraction_toggle_setting_off,
                    Toast.LENGTH_SHORT).show()
                SubtractingToggleButtonSetting = false
            }

        }
    }
}
