package com.example.smartcooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Patterns
import android.widget.Toast


class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.usernameRegisterEditText)
        emailEditText = findViewById(R.id.emailRegisterEditText)
        passwordEditText = findViewById(R.id.passwordRegisterEditText)

        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            if (validateInput(usernameEditText, emailEditText, passwordEditText)) {
                Toast.makeText(this,"Register Successful",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Register unsuccessful",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidUsername(username: String): Boolean {
        val regex = "^[a-zA-Z0-9._-]{3,}$"
        return regex.toRegex().matches(username)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$"
        return regex.toRegex().matches(password)
    }

    private fun validateInput(usernameEditText: EditText, emailEditText: EditText, passwordEditText: EditText): Boolean {
        var isValid = true

        if (!isValidUsername(usernameEditText.text.toString())) {
            usernameEditText.error = "Invalid username. Must be at least 3 characters and contain only letters, numbers, dots, underscores, and hyphens."
            isValid = false
        }

        if (!isValidEmail(emailEditText.text.toString())) {
            emailEditText.error = "Invalid email address."
            isValid = false
        }

        if (!isValidPassword(passwordEditText.text.toString())) {
            passwordEditText.error = "Invalid password. Must be at least 8 characters and contain at least one digit, lowercase letter, uppercase letter, and special character."
            isValid = false
        }

        return isValid
    }
}

