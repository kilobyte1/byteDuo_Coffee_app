package com.example.byteduo.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import com.example.byteduo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener() {
            //animate the button on create

            applyShadowAnimation(btnGetStarted)
            // Animate the button's shadow when the activity is created
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
    }

    private fun applyShadowAnimation(view: View) {
        // Animate the button with a shadow-like effect
        view.animate()
            .scaleX(3.1f)
            .scaleY(3.1f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                // After the animation completes, reset the scale to its original size
                view.scaleX = 1f
                view.scaleY = 1f
            }
            .start()
    }
}