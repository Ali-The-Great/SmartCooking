package com.example.smartcooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.LoginBtn)
        val registerButton = findViewById<Button>(R.id.registerBtn)

        loginButton.setOnClickListener {
            // Navigate to the Log in page
            // You can replace LoginActivity with the name of your login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Navigate to the Register page
            // You can replace RegisterActivity with the name of your register activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}