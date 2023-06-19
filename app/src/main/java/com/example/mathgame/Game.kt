package com.example.mathgame

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

class Game : AppCompatActivity() {

    companion object{
        var correctAnswers : Int = 0
        var incorrectAnswers: Int = 0
        var quitGameButtonTaps: Int = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // HIDE KEYBOARD AFTER TAP ON THE BUTTON
        fun View.hideSoftInput() {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }

        // CHANGE STROKE AND HEADER COLOUR AFTER CORRECT/INCORRECT ANSWER
        class AnswerInputFieldIndicator {
            val userInputHeader = findViewById<TextView>(R.id.et_game_user_answer_card_header)
            val userInputStroke = findViewById<MaterialCardView>(R.id.cv_game_user_answer_card)

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
                    Thread.sleep(4_00)
                    userInputHeader.setTextColor(Color.parseColor("#787586"))
                    userInputStroke.strokeColor = Color.parseColor("#413F4F")
                }
            }
        }

        //ChooseGame.source
        val sourceAdding = getString(R.string.game_settings_screen_source_adding)
        val sourceSubtracting = getString(R.string.game_settings_screen_source_subtracting)
        val sourceMultiplying = getString(R.string.game_settings_screen_source_multiplying)
        val sourceDividing = getString(R.string.game_settings_screen_source_dividing)

        // MATCH GAME SCREEN HEADER WITH ChooseGame.source variable
        val gameHeader: TextView = findViewById(R.id.et_game_header)
        when (ChooseGame.source){
            sourceAdding -> gameHeader.text = getString(R.string.game_adding_header)
            sourceSubtracting -> gameHeader.text = getString(R.string.game_subtracting_header)
            sourceMultiplying -> gameHeader.text = getString(R.string.game_multiplying_header)
            sourceDividing -> gameHeader.text = getString(R.string.game_dividing_header)
        }

        // MATH GAME SIGN WITH ChooseGame.source
        val gameSign: TextView = findViewById(R.id.et_game_sign)
        when (ChooseGame.source) {
            sourceAdding -> gameSign.text = getString(R.string.game_adding_sign)
            sourceSubtracting -> gameSign.text = getString(R.string.game_subtracting_sign)
            sourceMultiplying -> gameSign.text = getString(R.string.game_multiplying_sign)
            sourceDividing -> gameSign.text = getString(R.string.game_dividing_sign)
        }

        var numberOfRounds : Int = GameSettings.GameRounds.text.toString().toInt()
        val rangeLimit : Int = GameSettings.GameRange.text.toString().toInt()+1
        val btnCheckAddingResult : Button = findViewById(R.id.btn_game_check_result)
        val usersAnswer : EditText = findViewById(R.id.it_game_user_input_value)
        val firstNumberField = findViewById<TextView>(R.id.et_game_first_number)
        val secondNumberField = findViewById<TextView>(R.id.et_game_second_number)
        var currentPoints = findViewById<TextView>(R.id.et_game_current_score_value)
        var roundsLeftCounter = findViewById<TextView>(R.id.et_game_rounds_left_value)

        var firstNumber = 0
        var secondNumber = 0

        // ADJUSTING TEXT SIZE FOR 3 AND 4 DIGITS NUMBERS
        fun adjustNumberTextSize() {
            if (firstNumber.toString().count() == 3 || secondNumber.toString().count() == 3) {
                firstNumberField.textSize = 70F
                secondNumberField.textSize = 70F
                gameSign.textSize = 70F
            } else if (firstNumber.toString().count() == 4 || secondNumber.toString().count() == 4
            ) {
                firstNumberField.textSize = 55F
                secondNumberField.textSize = 55F
                gameSign.textSize = 55F
            } else {
                firstNumberField.textSize = 80F
                secondNumberField.textSize = 80F
                gameSign.textSize = 80F
            }
        }

        // MATCH MATH OPERATION WITH ChooseGame.source VARIABLE
        fun result(): Int {
            var result = 0
            when (ChooseGame.source) {
                sourceAdding -> result = firstNumber + secondNumber
                sourceSubtracting -> result = firstNumber - secondNumber
                sourceMultiplying -> result = firstNumber * secondNumber
                sourceDividing -> result = firstNumber / secondNumber
            }
            return result
        }


        // ADJUST RANDOM NUMBERS TO DIVIDING (DOUBLE instead of INT)
        fun generateNumbers() {
            if (ChooseGame.source == sourceDividing) {
                firstNumber = Random.nextInt(from = 1, until = rangeLimit)
                secondNumber = Random.nextInt(from = 2, until = rangeLimit)
                // FUN TO MAKE DIVIDING RESULT ONLY INTEGERS
                fun noFractionsResult() {
                    while (firstNumber.toDouble() % secondNumber.toDouble() != 0.0 || firstNumber <= secondNumber) {
                        firstNumber = Random.nextInt(from = 1, until = rangeLimit)
                        secondNumber = Random.nextInt(from = 2, until = rangeLimit)
                        firstNumber.toDouble()
                        secondNumber.toDouble()
                    }
                }

                noFractionsResult()
                adjustNumberTextSize()
                firstNumberField.text = firstNumber.toString()
                secondNumberField.text = secondNumber.toString()
                firstNumber.toDouble()
                secondNumber.toDouble()
            } else {
                firstNumber = Random.nextInt(from = 0, until = rangeLimit)
                secondNumber = Random.nextInt(from = 0, until = rangeLimit)
                // SETTING KEYBOARD TYPE TO TEXT FOR NEGATIVE SUBTRACTION RESULTS
                if (ChooseGame.source == sourceSubtracting) {
                    usersAnswer.inputType = InputType.TYPE_CLASS_TEXT
                }
                // SUBTRACTING FUN TO PREVENT NEGATIVE RESULTS DRIVEN BY TOGGLE BUTTON
                fun noNegativeResults() {
                    if (!GameSettings.SubtractingToggleButtonSetting) {
                        override@ usersAnswer.inputType = InputType.TYPE_CLASS_NUMBER
                        while (firstNumber < secondNumber) {
                            firstNumber = Random.nextInt(from = 0, until = rangeLimit)
                            secondNumber = Random.nextInt(from = 0, until = rangeLimit)
                        }
                    }
                }
                result()
                noNegativeResults()
                adjustNumberTextSize()
                firstNumberField.text = firstNumber.toString()
                secondNumberField.text = secondNumber.toString()
            }
            // FILLING POINTS AND ROUNDS LEFT AT THE BEGINNING OF THE GAME
            currentPoints.text = correctAnswers.toString()
            roundsLeftCounter.text = numberOfRounds.toString()
            usersAnswer.setText("")
            quitGameButtonTaps = 2
            numberOfRounds -= 1
        }

        // ######################
        // ### START THE GAME ###
        // ######################

        generateNumbers()



        // BUTTON ACTIONS
        btnCheckAddingResult.setOnClickListener {
            it.hideSoftInput()
            // TODO after EndGame.kt is completed
            if (numberOfRounds == 0) {
                val intent = Intent(this, EndGame::class.java)
                startActivity(intent)
            }
            if (usersAnswer.text.isEmpty()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_empty_result,
                    Toast.LENGTH_SHORT
                ).show()
                AnswerInputFieldIndicator().incorrectAnswer()
                AnswerInputFieldIndicator().reverseChange()
                quitGameButtonTaps = 2

            } else if (result().toString() == usersAnswer.text.toString()) {
                Toast.makeText(
                    this, R.string.adding_game_screen_correct_result,
                    Toast.LENGTH_SHORT
                ).show()
                AnswerInputFieldIndicator().correctAnswer()
                AnswerInputFieldIndicator().reverseChange()
                correctAnswers += 1
                generateNumbers()
            } else {
                Toast.makeText(
                    this, R.string.adding_game_screen_incorrect_result,
                    Toast.LENGTH_SHORT
                ).show()
                AnswerInputFieldIndicator().incorrectAnswer()
                AnswerInputFieldIndicator().reverseChange()
                incorrectAnswers += 1
                generateNumbers()
            }
        }

        val btnQuitGame: Button = findViewById(R.id.btn_game_quit)
        btnQuitGame.setOnClickListener {
            quitGameButtonTaps -= 1
            if (quitGameButtonTaps == 1) {
                Toast.makeText(
                    this, R.string.adding_game_screen_quit_game_taps_description,
                    Toast.LENGTH_SHORT
                ).show()
            }else if (quitGameButtonTaps == 0) {
                // TODO after EndGame.kt is completed
                val intent = Intent(this, EndGame::class.java)
                startActivity(intent)
                finish()
            }
        }


    }
}