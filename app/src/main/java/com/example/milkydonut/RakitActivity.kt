package com.example.milkydonut

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RakitActivity : AppCompatActivity() {

    private lateinit var donutBesar: DonutView
    private lateinit var tvNamaDonat: TextView
    private lateinit var tvRincian: TextView
    private lateinit var tvTotal: TextView

    private var rasaDipilih: Rasa = daftarRasa[0]
    private val toppingDipilih = mutableListOf<String>()
    private val bungkusRasa = mutableListOf<Pair<Rasa, LinearLayout>>()
    private val kartuTopping = mutableMapOf<String, MaterialCardView>()
    private val badgeTopping = mutableMapOf<String, TextView>()

    private val emoji = mapOf(
        "Meses" to "🌈", "Choco chips" to "🍫", "Keju parut" to "🧀",
        "Crumble" to "🍪", "Oreo" to "🖤", "Almond" to "🌰",
        "Kelapa parut" to "🥥", "Marshmallow" to "☁️", "Karamel" to "🍯",
        "Wafer" to "🧇", "Selai stroberi" to "🍓", "Boba mini" to "🧋"
    )

    private fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rakit)

        val namaRasa = intent.getStringExtra("rasa")
        rasaDipilih = daftarRasa.firstOrNull { it.nama == namaRasa } ?: daftarRasa[0]

        tvNamaDonat = findViewById(R.id.tvNamaDonat)
        tvRincian = findViewById(R.id.tvRincian)
        tvTotal = findViewById(R.id.tvTotal)

        donutBesar = DonutView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        findViewById<FrameLayout>(R.id.wadahDonat).addView(donutBesar)

        findViewById<TextView>(R.id.btnKembali).setOnClickListener { finish() }

        val rowRasa = findViewById<LinearLayout>(R.id.rowRasa)
        for (rasa in daftarRasa) {
            val bungkus = LinearLayout(this).apply {
                setPadding(dp(4), dp(4), dp(4), dp(4))
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(dp(4), dp(4), dp(4), dp(4)) }
                setOnClickListener {
                    rasaDipilih = rasa
                    perbarui()
                    goyang()
                }
            }
            val bulat = DonutView(this).apply {
                glaze = rasa.warna
                face = false
                layoutParams = LinearLayout.LayoutParams(dp(52), dp(52))
                isClickable = false
            }
            bungkus.addView(bulat)
            rowRasa.addView(bungkus)
            bungkusRasa.add(Pair(rasa, bungkus))
        }

        val gridTopping = findViewById<GridLayout>(R.id.gridTopping)
        for (nama in daftarTopping) {
            val kartu = MaterialCardView(this).apply {
                radius = dp(18).toFloat()
                cardElevation = 0f
                strokeWidth = dp(2)
                isClickable = true
                isFocusable = true
                layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)
                ).apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    setMargins(dp(5), dp(5), dp(5), dp(5))
                }
            }
            val baris = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(12), dp(12), dp(12), dp(12))
            }
            val ikon = TextView(this).apply {
                text = emoji[nama] ?: "🍩"
                textSize = 24f
            }
            val teks = TextView(this).apply {
                text = nama
                textSize = 15f
                setTextColor(Color.parseColor("#4A1B0C"))
                layoutParams = LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
                ).apply { marginStart = dp(8) }
            }
            val badge = TextView(this).apply {
                text = "ada ✓"
                textSize = 12f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#D4537E"))
                visibility = View.GONE
            }
            baris.addView(ikon)
            baris.addView(teks)
            baris.addView(badge)
            kartu.addView(baris)
            kartu.setOnClickListener {
                if (nama in toppingDipilih) toppingDipilih.remove(nama) else toppingDipilih.add(nama)
                perbarui()
                goyang()
            }
            gridTopping.addView(kartu)
            kartuTopping[nama] = kartu
            badgeTopping[nama] = badge
        }

        findViewById<MaterialButton>(R.id.btnTambah).setOnClickListener {
            Skor.donat += 1
            Skor.poin += 10 + toppingDipilih.size * 5
            Toast.makeText(this, "Yeay! Donat ${rasaDipilih.nama} masuk pesanan 🍩", Toast.LENGTH_SHORT).show()
            finish()
        }

        perbarui()
    }

    private fun perbarui() {
        donutBesar.glaze = rasaDipilih.warna
        donutBesar.toppings = toppingDipilih.toList()
        tvNamaDonat.text = "Donat ${rasaDipilih.nama} + ${toppingDipilih.size} topping"

        for ((rasa, bungkus) in bungkusRasa) {
            if (rasa == rasaDipilih) {
                val ring = GradientDrawable()
                ring.shape = GradientDrawable.OVAL
                ring.setColor(Color.TRANSPARENT)
                ring.setStroke(dp(3), Color.parseColor("#D4537E"))
                bungkus.background = ring
            } else {
                bungkus.background = null
            }
        }

        for (nama in daftarTopping) {
            val aktif = nama in toppingDipilih
            kartuTopping[nama]?.apply {
                setCardBackgroundColor(Color.parseColor(if (aktif) "#FBEAF0" else "#FFFFFF"))
                strokeColor = Color.parseColor(if (aktif) "#D4537E" else "#F4C0D1")
            }
            badgeTopping[nama]?.visibility = if (aktif) View.VISIBLE else View.GONE
        }

        val total = 8000 + 1500 * toppingDipilih.size
        tvRincian.text = "Donat Rp 8.000 + ${toppingDipilih.size} topping x Rp 1.500"
        tvTotal.text = "Rp " + "%,d".format(total).replace(',', '.')
    }

    private fun goyang() {
        donutBesar.animate().scaleX(1.12f).scaleY(1.12f).setDuration(110).withEndAction {
            donutBesar.animate().scaleX(1f).scaleY(1f).setDuration(110).start()
        }.start()
    }
}