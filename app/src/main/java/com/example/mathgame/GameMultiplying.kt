package com.example.mathgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class GameMultiplying : AppCompatActivity() {

    companion object{
        var correctAnswers : Int = 0
        var incorrectAnswers: Int = 0
        var quitGameButtonTaps: Int = 2
    }

    override fun onBackPressed() {
        Toast.makeText(
            this, R.string.adding_game_screen_back_button_disabled_info,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_multiplying)

        fun View.hideSoftInput() {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }

        var numberOfRounds : Int = GameSettings.GameRounds.text.toString().toInt()
        val numbersRangeMaxLimit : Int = GameSettings.GameRange.text.toString().toInt()+1
        val btnCheckMultiplyingResult : Button = findViewById(R.id.btn_multiplying_check_result)
        val userResult : EditText = findViewById(R.id.et_multiplying_user_result_input)
        var firstNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var secondNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var multiplyingResult: Int = firstNumber * secondNumber
        findViewById<TextView>(R.id.et_multiplying_first_number).text = firstNumber.toString()
        findViewById<TextView>(R.id.et_multiplying_second_number).text = secondNumber.toString()
        findViewById<TextView>(R.id.et_multiplying_current_score_value).text = correctAnswers.toString()
        findViewById<TextView>(R.id.et_multiplying_rounds_left_value).text = numberOfRounds.toString()

        fun newNumbersAndUpdateScore(){
            firstNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            secondNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            multiplyingResult = firstNumber * secondNumber
            findViewById<TextView>(R.id.et_multiplying_first_number).text = firstNumber.toString()
            findViewById<TextView>(R.id.et_multiplying_second_number).text = secondNumber.toString()
            findViewById<TextView>(R.id.et_multiplying_current_score_value).text = correctAnswers.toString()
            numberOfRounds -= 1
            findViewById<TextView>(R.id.et_multiplying_rounds_left_value).text = numberOfRounds.toString()
            userResult.setText("")
        }

        btnCheckMultiplyingResult.setOnClickListener {
            if (userResult.text.isEmpty()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_empty_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2

            } else if (multiplyingResult.toString() == userResult.text.toString()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_correct_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2
                GameMultiplying.correctAnswers += 1
                it.hideSoftInput()
                newNumbersAndUpdateScore()
            } else {
                Toast.makeText(
                    this, R.string.adding_game_screen_incorrect_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2
                incorrectAnswers += 1
                it.hideSoftInput()
                newNumbersAndUpdateScore()
            }
            if (numberOfRounds == 0) {
                val intent = Intent(this, EndGameMultiplying::class.java)
                startActivity(intent)
            }
        }

        val btnQuitGame: Button = findViewById(R.id.btn_multiplying_quit_game)
        btnQuitGame.setOnClickListener {
            quitGameButtonTaps -= 1
            if (quitGameButtonTaps == 1) {
                Toast.makeText(
                    this, R.string.adding_game_screen_quit_game_taps_description,
                    Toast.LENGTH_SHORT
                ).show()
            }else if (quitGameButtonTaps == 0) {
                val intent = Intent(this, EndGameMultiplying::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}