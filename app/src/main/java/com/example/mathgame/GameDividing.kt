package com.example.mathgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class GameDividing : AppCompatActivity() {

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
        setContentView(R.layout.activity_game_dividing)

        fun View.hideSoftInput() {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }

        var numberOfRounds : Int = GameSettings.GameRounds.text.toString().toInt()
        val numbersRangeMaxLimit : Int = GameSettings.GameRange.text.toString().toInt()+1
        val btnCheckDividingResult : Button = findViewById(R.id.btn_dividing_check_result)
        val userResult : EditText = findViewById(R.id.et_dividing_user_result_input)
        val firsNumberField = findViewById<TextView>(R.id.et_dividing_first_number)
        val secondNumberField = findViewById<TextView>(R.id.et_dividing_second_number)
        val signField = findViewById<TextView>(R.id.et_dividing_plus_sign)

        var firstNumber: Double = Random.nextInt(from = 1, until = numbersRangeMaxLimit).toDouble()
        var secondNumber: Double = Random.nextInt(from = 2, until = numbersRangeMaxLimit).toDouble()

        fun noFractionsResult() {
            while (firstNumber % secondNumber != 0.0 || firstNumber <= secondNumber) {
                firstNumber = Random.nextInt(from = 1, until = numbersRangeMaxLimit).toDouble()
                secondNumber = Random.nextInt(from = 2, until = numbersRangeMaxLimit).toDouble()
            }
        }

        noFractionsResult()

        var dividingResult: Double = firstNumber / secondNumber
        firsNumberField.text = firstNumber.toInt().toString()
        secondNumberField.text = secondNumber.toInt().toString()

        fun adjustNumberTextSize() {
            if (firstNumber.toInt().toString().count() == 3 || secondNumber.toInt().toString()
                    .count() == 3
            ) {
                firsNumberField.textSize = 70F
                secondNumberField.textSize = 70F
                signField.textSize = 70F
            } else if (firstNumber.toInt().toString().count() == 4 || secondNumber.toInt()
                    .toString().count() == 4
            ) {
                firsNumberField.textSize = 55F
                secondNumberField.textSize = 55F
                signField.textSize = 55F
            } else {
                firsNumberField.textSize = 80F
                secondNumberField.textSize = 80F
                signField.textSize = 80F
            }
        }

        adjustNumberTextSize()

        findViewById<TextView>(R.id.et_dividing_current_score_value).text = correctAnswers.toString()
        findViewById<TextView>(R.id.et_dividing_rounds_left_value).text = numberOfRounds.toString()

        fun newNumbersAndUpdateScore(){
            firstNumber = Random.nextInt(from = 1, until = numbersRangeMaxLimit).toDouble()
            secondNumber = Random.nextInt(from = 2, until = numbersRangeMaxLimit).toDouble()
            noFractionsResult()
            dividingResult = firstNumber / secondNumber
            firsNumberField.text = firstNumber.toInt().toString()
            secondNumberField.text = secondNumber.toInt().toString()
            adjustNumberTextSize()
            findViewById<TextView>(R.id.et_dividing_current_score_value).text = correctAnswers.toString()
            numberOfRounds -= 1
            findViewById<TextView>(R.id.et_dividing_rounds_left_value).text = numberOfRounds.toString()
            userResult.setText("")
        }

        btnCheckDividingResult.setOnClickListener {
            if (userResult.text.isEmpty()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_empty_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2

            } else if (dividingResult.toInt().toString() == userResult.text.toString()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_correct_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2
                correctAnswers += 1
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
                val intent = Intent(this, EndGameDividing::class.java)
                startActivity(intent)
            }
        }

        val btnQuitGame: Button = findViewById(R.id.btn_dividing_quit_game)
        btnQuitGame.setOnClickListener {
            quitGameButtonTaps -= 1
            if (quitGameButtonTaps == 1) {
                Toast.makeText(
                    this, R.string.adding_game_screen_quit_game_taps_description,
                    Toast.LENGTH_SHORT
                ).show()
            }else if (quitGameButtonTaps == 0) {
                val intent = Intent(this, EndGameDividing::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}
