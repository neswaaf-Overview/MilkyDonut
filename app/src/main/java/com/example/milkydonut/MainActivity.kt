package com.example.milkydonut

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var tvSkor: TextView
    private lateinit var tvDonat: TextView

    private fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSkor = findViewById(R.id.tvSkor)
        tvDonat = findViewById(R.id.tvDonat)
        val grid = findViewById<GridLayout>(R.id.grid)
        val btnAcak = findViewById<MaterialButton>(R.id.btnAcak)

        for (rasa in daftarRasa) {
            grid.addView(buatKartu(rasa))
        }

        btnAcak.setOnClickListener {
            val rasa = daftarRasa.random()
            pilihRasa(rasa)
        }
    }

    override fun onResume() {
        super.onResume()
        tvSkor.text = "⭐ Skor: ${Skor.poin}"
        tvDonat.text = "🍩 Donat jadi: ${Skor.donat}"
    }

    private fun pilihRasa(rasa: Rasa) {
        val intent = Intent(this, RakitActivity::class.java)
        intent.putExtra("rasa", rasa.nama)
        startActivity(intent)
    }

    private fun buatKartu(rasa: Rasa): View {
        val kartu = MaterialCardView(this).apply {
            radius = dp(20).toFloat()
            cardElevation = dp(2).toFloat()
            setCardBackgroundColor(Color.WHITE)
            strokeColor = rasa.tepi
            strokeWidth = dp(2)
            isClickable = true
            isFocusable = true
            layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            ).apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                setMargins(dp(6), dp(6), dp(6), dp(6))
            }
        }

        val isi = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(8), dp(14), dp(8), dp(14))
        }

        val donat = DonutView(this).apply {
            glaze = rasa.warna
            toppings = listOf("Meses")
            layoutParams = LinearLayout.LayoutParams(dp(90), dp(90))
        }

        val nama = TextView(this).apply {
            text = rasa.nama
            textSize = 17f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.parseColor("#4A1B0C"))
            gravity = Gravity.CENTER
            setPadding(0, dp(8), 0, 0)
        }

        isi.addView(donat)
        isi.addView(nama)
        kartu.addView(isi)
        kartu.setOnClickListener { pilihRasa(rasa) }
        return kartu
    }
}