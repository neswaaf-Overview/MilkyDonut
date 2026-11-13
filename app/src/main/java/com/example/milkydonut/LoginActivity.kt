
package com.example.milkydonut

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Email dan password harus diisi!",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (email == "admin@milkydonut.com"
                && password == "123456") {

                Toast.makeText(
                    this,
                    "Login berhasil! Selamat datang 💗",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Email atau password salah!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
