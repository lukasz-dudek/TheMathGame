package com.example.mathgame

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class GameAdding : AppCompatActivity() {

    companion object{
        var correctAnswers : Int = 0
        var incorrectAnswers: Int = 0
        var quitGameButtonTaps: Int = 2
    }

    // TODO copy function to Game.kt
    override fun onBackPressed() {
        Toast.makeText(
            this, R.string.adding_game_screen_back_button_disabled_info,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_adding)

        fun View.hideSoftInput() {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }

        class AnswerInputFieldIndicator {
            val userInputHeader = findViewById<TextView>(R.id.et_adding_user_result_card_header)
            val userInputStroke = findViewById<MaterialCardView>(R.id.cv_adding_user_answer)

            fun correctAnswer() {
                userInputHeader.setTextColor(Color.parseColor("#00AC7C"))
                userInputStroke.strokeColor = Color.parseColor("#00AC7C")
            }

            fun incorrectAnswer() {
                userInputHeader.setTextColor(Color.parseColor("#F03A47"))
                userInputStroke.strokeColor = Color.parseColor("#F03A47")
            }

            fun reverseChange() {
                Handler(Looper.getMainLooper()).post {
                    Thread.sleep(5_00)
                    userInputHeader.setTextColor(Color.parseColor("#787586"))
                    userInputStroke.strokeColor = Color.parseColor("#413F4F")
                }
            }
        }

        var numberOfRounds : Int = GameSettings.GameRounds.text.toString().toInt()
        val numbersRangeMaxLimit : Int = GameSettings.GameRange.text.toString().toInt()+1
        val btnCheckAddingResult : Button = findViewById(R.id.btn_adding_check_result)
        val userResult : EditText = findViewById(R.id.et_adding_user_result_input)
        val firsNumberField = findViewById<TextView>(R.id.et_adding_first_number)
        val secondNumberField = findViewById<TextView>(R.id.et_adding_second_number)
        val signField = findViewById<TextView>(R.id.et_adding_plus_sign)

        var firstNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var secondNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var addingResult: Int = firstNumber + secondNumber
        firsNumberField.text = firstNumber.toString()
        secondNumberField.text = secondNumber.toString()

        fun adjustNumberTextSize() {
            if (firstNumber.toString().count() == 3 || secondNumber.toString().count() == 3) {
                firsNumberField.textSize = 70F
                secondNumberField.textSize = 70F
                signField.textSize = 70F
            } else if (firstNumber.toString().count() == 4 || secondNumber.toString().count() == 4
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

        findViewById<TextView>(R.id.et_adding_current_score_value).text = correctAnswers.toString()
        findViewById<TextView>(R.id.et_adding_rounds_left_value).text = numberOfRounds.toString()

        fun newNumbersAndUpdateScore(){
            firstNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            secondNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            addingResult = firstNumber + secondNumber
            firsNumberField.text = firstNumber.toString()
            secondNumberField.text = secondNumber.toString()
            adjustNumberTextSize()
            findViewById<TextView>(R.id.et_adding_current_score_value).text = correctAnswers.toString()
            numberOfRounds -= 1
            findViewById<TextView>(R.id.et_adding_rounds_left_value).text = numberOfRounds.toString()
            userResult.setText("")
            }

        btnCheckAddingResult.setOnClickListener {
            if (userResult.text.isEmpty()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_empty_result,
                    Toast.LENGTH_SHORT
                ).show()
                AnswerInputFieldIndicator().incorrectAnswer()
                AnswerInputFieldIndicator().reverseChange()
                quitGameButtonTaps = 2

            } else if (addingResult.toString() == userResult.text.toString()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_correct_result,
                    Toast.LENGTH_SHORT
                ).show()
                it.hideSoftInput()
                AnswerInputFieldIndicator().correctAnswer()
                AnswerInputFieldIndicator().reverseChange()
                quitGameButtonTaps = 2
                correctAnswers += 1
                newNumbersAndUpdateScore()
            } else {
                Toast.makeText(
                    this, R.string.adding_game_screen_incorrect_result,
                    Toast.LENGTH_SHORT
                ).show()
                it.hideSoftInput()
                AnswerInputFieldIndicator().incorrectAnswer()
                AnswerInputFieldIndicator().reverseChange()
                quitGameButtonTaps = 2
                incorrectAnswers += 1
                newNumbersAndUpdateScore()
            }
            if (numberOfRounds == 0) {
                val intent = Intent(this, EndGameAdding::class.java)
                startActivity(intent)
            }
        }

        val btnQuitGame: Button = findViewById(R.id.btn_adding_quit_game)
        btnQuitGame.setOnClickListener {
            quitGameButtonTaps -= 1
            if (quitGameButtonTaps == 1) {
                Toast.makeText(
                    this, R.string.adding_game_screen_quit_game_taps_description,
                    Toast.LENGTH_SHORT
                ).show()
            }else if (quitGameButtonTaps == 0) {
                val intent = Intent(this, EndGameAdding::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}
