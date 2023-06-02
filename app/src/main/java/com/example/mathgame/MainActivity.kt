package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var etUserName: EditText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        etUserName = findViewById(R.id.et_user_name)
        val btnStart : Button = findViewById(R.id.btn_start)
        btnStart.setOnClickListener {

            if(etUserName.text.isEmpty()){
                etUserName.error = getString(R.string.welcome_screen_empty_name_error)
            }else{
                val intent = Intent(this, ChooseGame::class.java)
                startActivity(intent)
                finish()
            }
        }


    }
}