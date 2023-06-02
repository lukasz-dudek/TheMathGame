package com.example.mathgame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView



class EndGameAdding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game_adding)

        fun resetGameResults(){
            GameAdding.correctAnswers = 0
            GameAdding.incorrectAnswers = 0
            GameAdding.quitGameButtonTaps = 0
        }

        val finalCorrectAnswers: Int = GameAdding.correctAnswers
        val finalIncorrectAnswers: Int = GameAdding.incorrectAnswers
        var finalPercentage: Int = 0

        if (finalCorrectAnswers+finalIncorrectAnswers != 0){
            finalPercentage = ((100*finalCorrectAnswers) / (finalCorrectAnswers+finalIncorrectAnswers))
        }
        val btnReplay: Button = findViewById(R.id.btn_adding_replay)
        val btnQuitGame: Button = findViewById(R.id.btn_adding_quit)

        findViewById<TextView>(R.id.final_correct_answers).text = finalCorrectAnswers.toString()
        findViewById<TextView>(R.id.final_incorrect_answers).text = finalIncorrectAnswers.toString()
        findViewById<TextView>(R.id.final_percentage).text = finalPercentage.toString()

        val finalScore = findViewById<TextView>(R.id.final_score)
        if (finalCorrectAnswers+finalIncorrectAnswers == 0) {
            finalScore.text = ""
        }else if (finalPercentage >= 95){
            finalScore.setTextColor(Color.parseColor("#399E5A"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_a)
        }else if (finalPercentage >= 88){
            finalScore.setTextColor(Color.parseColor("#006C67"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_b)
        }else if (finalPercentage >= 75){
            finalScore.setTextColor(Color.parseColor("#FFB20F"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_c)
        }else if (finalPercentage >= 67){
            finalScore.setTextColor(Color.parseColor("#FF7F11"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_d)
        }else if (finalPercentage >=30){
            finalScore.setTextColor(Color.parseColor("#FA824C"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_e)
        }else {
            finalScore.setTextColor(Color.parseColor("#FF1B1C"))
            finalScore.text = getText(R.string.end_game_screen_summary_end_score_f)
        }


        btnReplay.setOnClickListener {
            val intent = Intent (this, SettingsGameAdding::class.java)
            startActivity(intent)
            resetGameResults()
        }

        btnQuitGame.setOnClickListener {
            val intent = Intent (this, ChooseGame::class.java)
            startActivity(intent)
            resetGameResults()
        }



    }
}