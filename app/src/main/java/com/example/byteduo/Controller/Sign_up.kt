package com.example.byteduo.Controller

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.byteduo.R
import com.example.byteduo.model.Customer
import com.example.byteduo.model.FirebaseDBManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class Sign_up : AppCompatActivity() {

    lateinit var fullNameInput: TextInputEditText
    lateinit var  usernameInput: TextInputEditText
    lateinit var emailInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var confirmPasswordInput: TextInputEditText
    lateinit var mobileNumberInput: TextInputEditText
    lateinit var btnRegister: Button
    lateinit var mAuth: FirebaseAuth
    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()

        // UI initialization
        btnRegister = findViewById(R.id.btnRegister)
        fullNameInput = findViewById(R.id.etFullName)
        usernameInput = findViewById(R.id.etUserName)
        emailInput = findViewById(R.id.etEmailAddress)
        passwordInput = findViewById(R.id.etPassword)
        confirmPasswordInput = findViewById(R.id.etConfirmPassword)
        mobileNumberInput = findViewById(R.id.etMobileNumber)


        // Animate the button's shadow when the activity is created
        applyShadowAnimation(btnRegister)

        val signup = findViewById<TextView>(R.id.txtAlreadyHaveAnAccount)

        //when the user clicks on the sign in text on the sign up page,
        //it should take the user to the sign in page
        signup.setOnClickListener() {
            val intent = Intent(this, Sign_in::class.java)

            startActivity(intent)
        }

        btnRegister.setOnClickListener() {
            registerUser()
        }

    }

    //regular expression for password
    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,}\$".toRegex()
    private fun registerUser(){
        try {
            val fullName = fullNameInput.text.toString().trim()
            val username = usernameInput.text.toString().trim()
            val mobile = mobileNumberInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Set maximum lengths
            val maxFullNameLength = 50
            val maxUsernameLength = 20
            val maxMobileLength = 15
            val maxEmailLength = 100
            val maxPasswordLength = 20
            val maxConfirmPasswordLength = 20

            if (fullName.isEmpty() || username.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Show error: All fields are required
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return
            } else if (fullName.length < 2) {
                fullNameInput.setError("Full name should not be less than 2 characters")
                fullNameInput.requestFocus()
                return

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Show error: Invalid Email Address
                emailInput.setError("Please enter a valid Email Address")
                emailInput.requestFocus()
                return
            } else if (password != confirmPassword) {
                // Show error: Password does not match
                confirmPasswordInput.setError("Password does not match")
                confirmPasswordInput.requestFocus()
                return
            } else if (password.length < 6) {
                // Show error: Password must be at least 6 characters
                passwordInput.setError("Password must be at least 6 characters")
                passwordInput.requestFocus()
                return
            } else if (!passwordRegex.matches(password)) {
                // Show error: Password does not meet requirements
                passwordInput.setError("Password must include at least one lowercase letter, one uppercase letter, one digit, one special character among @, \$, !, %, *, ?, and &, and a minimum of 8 characters long.")
                passwordInput.requestFocus()
                return
            } else if (mobile.length < 10) {
                //sanitize mobile number
                mobileNumberInput.setError("Invalid mobile number. It must be at least 10 digits.")
                mobileNumberInput.requestFocus()
                return
            } else if (!mobile.matches(Regex("[0-9]+"))) {
                mobileNumberInput.setError("Invalid mobile number. It should only contain digits.")
                mobileNumberInput.requestFocus()
                return
            } else if (!mobile.startsWith("0")) {
                mobileNumberInput.setError("Invalid mobile number. It should start with 0")
                mobileNumberInput.requestFocus()
                return
            } else if (fullName.length > maxFullNameLength) {
                // Show error: Full name is too long
                fullNameInput.setError("Full name is too long (maximum $maxFullNameLength characters)")
                fullNameInput.requestFocus()
                return
            } else if (username.length > maxUsernameLength) {
                // Show error: Username is too long
                usernameInput.setError("Username is too long (maximum $maxUsernameLength characters)")
                usernameInput.requestFocus()
                return
            } else if (mobile.length > maxMobileLength) {
                // Show error: Mobile number is too long
                mobileNumberInput.setError("Mobile number is too long (maximum $maxMobileLength characters)")
                mobileNumberInput.requestFocus()
                return
            } else if (email.length > maxEmailLength) {
                // Show error: Email is too long
                emailInput.setError("Email is too long (maximum $maxEmailLength characters)")
                emailInput.requestFocus()
                return
            } else if (password.length > maxPasswordLength) {
                // Show error: Password is too long
                passwordInput.setError("Password is too long (maximum $maxPasswordLength characters)")
                passwordInput.requestFocus()
                return
            } else if (confirmPassword.length > maxConfirmPasswordLength) {
                // Show error: Confirmation password is too long
                confirmPasswordInput.setError("Confirmation password is too long (maximum $maxConfirmPasswordLength characters)")
                confirmPasswordInput.requestFocus()
                return
            } else {


                //database
                val inn = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inn.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_wait)
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()


                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Registration successful //make a toast
                            Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT)
                                .show()

                            val user = mAuth.currentUser
                            if (user != null) {


                                Log.d("SignUpActivity", "Values: $fullName, $email, $mobile, $username, $password, customer")
                                // Save user data to the database
                                val customer = Customer( fullName, email, mobile, username, password, "customer",true)
                                FirebaseDBManager.addCustomer(customer)
                            }

                            // Redirect to activity
                            val intent = Intent(this, CustomerMenu::class.java)
                            startActivity(intent)
                        } else {
                            // Registration failed
                            Toast.makeText(
                                this,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        }

                        // Dismiss the dialog regardless of success or failure
                        dialog.dismiss()
                    }
            }
        } catch (e: Exception) {
            //catch any other exceptions
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()

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