package com.example.byteduo.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import com.example.byteduo.R

class Sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnNext = findViewById<Button>(R.id.btnNext)


        // Animate the button's shadow when the activity is created
        applyShadowAnimation(btnNext)

        val signup = findViewById<TextView>(R.id.txtAlreadyHaveAnAccount)

        //when the user clicks on the sign in text on the sign up page,
        //it should take the user to the sign in page
        signup.setOnClickListener(){
            val intent = Intent(this, Sign_in::class.java)

            startActivity(intent)
        }

        btnNext.setOnClickListener(){
            val intent = Intent(this, Menu::class.java)

            startActivity(intent)
        }
    }


    //fun to animate next button
    private fun applyShadowAnimation(view: View) {
        // Animate the button with a shadow effect
        view.animate()
            .translationZ(10f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                // After the animation completes, reset the translation to remove the shadow
                view.translationZ = 0f
            }
            .start()
    }
}