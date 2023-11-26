package com.example.byteduo.UI

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.byteduo.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Sign_in : AppCompatActivity() {


    lateinit var emailInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var mAuth: FirebaseAuth
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signup = findViewById<TextView>(R.id.txtDontHaveAnAccount)
        val login = findViewById<Button>(R.id.btnLogin)
        emailInput = findViewById(R.id.etEmailAddress)
        passwordInput = findViewById(R.id.etPassword)


        mAuth = FirebaseAuth.getInstance()

        //when the user clicks on the sign in text on the sign up page,
        //it should take the user to the sign in page
        signup.setOnClickListener(){
            val intent = Intent(this, Sign_up::class.java)

            startActivity(intent)
        }

        login.setOnClickListener() {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            val maxEmailLength = 100
            val minPasswordLength = 6

            if (email.isEmpty() || password.isEmpty()) {
                // Show error: Both email and password are required
                Toast.makeText(this, "Both email and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val emailRegex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()
            if (email.length > maxEmailLength || !emailRegex.matches(email)) {
                // Show error: Invalid Email Address
                emailInput.setError("Please enter a valid Email Address (maximum $maxEmailLength characters)")
                emailInput.requestFocus()
                return@setOnClickListener
            }

            if (password.length < minPasswordLength) {
                // Show error: Password must be at least 6 characters
                passwordInput.setError("Password must be at least $minPasswordLength characters")
                passwordInput.requestFocus()
                return@setOnClickListener
            }

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_wait)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        if (user != null) {
                            val intent = Intent(this, Menu::class.java)
                            startActivity(intent)
                        }
                    } else {
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidUserException -> "User not found"
                            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
                            else -> "Login failed: ${task.exception?.message}"
                        }

                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        Log.e("LoginActivity", "Authentication failed: $errorMessage")
                    }

                    // Dismiss the dialog regardless of success or failure
                    dialog.dismiss()
                }
        }




    }
}