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


class GameAdding : AppCompatActivity() {

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
        setContentView(R.layout.activity_game_adding)

        fun View.hideSoftInput() {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }

        var numberOfRounds : Int = SettingsGameAdding.addingGameSettingsRounds.text.toString().toInt()
        val numbersRangeMaxLimit : Int = SettingsGameAdding.addingGameSettingsRange.text.toString().toInt()+1
        val btnCheckAddingResult : Button = findViewById(R.id.btn_check_adding_result)
        val userResult : EditText = findViewById(R.id.et_adding_user_result)
        var firstNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var secondNumber: Int = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
        var addingResult: Int = firstNumber + secondNumber
        findViewById<TextView>(R.id.et_first_number).text = firstNumber.toString()
        findViewById<TextView>(R.id.et_second_number).text = secondNumber.toString()
        findViewById<TextView>(R.id.user_adding_score).text = correctAnswers.toString()
        findViewById<TextView>(R.id.user_adding_rounds_left).text = numberOfRounds.toString()

        fun newNumbersAndUpdateScore(){
            firstNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            secondNumber = Random.nextInt(from = 0, until = numbersRangeMaxLimit)
            addingResult = firstNumber + secondNumber
            findViewById<TextView>(R.id.et_first_number).text = firstNumber.toString()
            findViewById<TextView>(R.id.et_second_number).text = secondNumber.toString()
            findViewById<TextView>(R.id.user_adding_score).text = correctAnswers.toString()
            numberOfRounds -= 1
            findViewById<TextView>(R.id.user_adding_rounds_left).text = numberOfRounds.toString()
            userResult.setText("")
        }

        btnCheckAddingResult.setOnClickListener {
            if (userResult.text.isEmpty()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_empty_result,
                    Toast.LENGTH_SHORT
                ).show()
                quitGameButtonTaps = 2

            } else if (addingResult.toString() == userResult.text.toString()) {
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
                val intent = Intent(this, EndGameAdding::class.java)
                startActivity(intent)
            }

        }

        val btnQuitGame: Button = findViewById(R.id.btn_quit_game)
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