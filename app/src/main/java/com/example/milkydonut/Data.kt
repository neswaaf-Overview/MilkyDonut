package com.example.milkydonut

import android.graphics.Color

data class Rasa(val nama: String, val warna: Int, val tepi: Int)

val daftarRasa = listOf(
    Rasa("Stroberi", Color.parseColor("#F4A6C0"), Color.parseColor("#ED93B1")),
    Rasa("Tiramisu", Color.parseColor("#D7B999"), Color.parseColor("#C9A27E")),
    Rasa("Cokelat", Color.parseColor("#6D4C41"), Color.parseColor("#8D6E63")),
    Rasa("Matcha", Color.parseColor("#A5D66F"), Color.parseColor("#97C459")),
    Rasa("Taro", Color.parseColor("#C9B6EE"), Color.parseColor("#AFA9EC")),
    Rasa("Blueberry", Color.parseColor("#7986CB"), Color.parseColor("#7F77DD"))
)

val daftarTopping = listOf(
    "Meses", "Choco chips", "Keju parut", "Crumble",
    "Oreo", "Almond", "Kelapa parut", "Marshmallow",
    "Karamel", "Wafer", "Selai stroberi", "Boba mini"
)

object Skor {
    var poin = 0
    var donat = 0
}