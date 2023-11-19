package com.example.byteduo.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.byteduo.R

class Sign_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signin = findViewById<TextView>(R.id.txtDontHaveAnAccount)
        val next = findViewById<Button>(R.id.btnNext)

        //when the user clicks on the sign in text on the sign up page,
        //it should take the user to the sign in page
        signin.setOnClickListener(){
            val intent = Intent(this, Sign_up::class.java)

            startActivity(intent)
        }

        next.setOnClickListener(){
            val intent = Intent(this, Menu::class.java)

            startActivity(intent)
        }



    }
}