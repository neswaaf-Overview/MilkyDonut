package com.example.milkydonut

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class DonutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ring = Path()

    var glaze: Int = Color.parseColor("#F4A6C0")
        set(value) { field = value; invalidate() }

    var toppings: List<String> = emptyList()
        set(value) { field = value; invalidate() }

    var face: Boolean = true
        set(value) { field = value; invalidate() }

    private val mesesWarna = listOf(
        "#E24B4A", "#378ADD", "#EF9F27", "#639922", "#FFFFFF", "#D4537E"
    ).map { Color.parseColor(it) }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        val r = minOf(width, height) / 2f - 2f
        if (r <= 0f) return
        val lubang = r * 0.3f

        ring.reset()
        ring.fillType = Path.FillType.EVEN_ODD
        ring.addCircle(cx, cy, r, Path.Direction.CW)
        ring.addCircle(cx, cy, lubang, Path.Direction.CW)
        paint.style = Paint.Style.FILL
        paint.color = glaze
        canvas.drawPath(ring, paint)

        for (nama in toppings) {
            val rnd = Random(nama.hashCode())
            val jumlah = when (nama) {
                "Oreo" -> 3
                "Wafer" -> 3
                "Marshmallow" -> 5
                "Karamel" -> 4
                "Selai stroberi" -> 4
                "Almond" -> 5
                else -> 8
            }
            for (i in 0 until jumlah) {
                val sudut = rnd.nextFloat() * 6.2832f
                val jarak = lubang + (r - lubang) * (0.25f + rnd.nextFloat() * 0.5f)
                val x = cx + cos(sudut) * jarak
                val y = cy + sin(sudut) * jarak
                gambarTopping(canvas, nama, x, y, r, rnd)
            }
        }

        if (face) gambarWajah(canvas, cx, cy, r, lubang)
    }

    private fun gambarTopping(c: Canvas, nama: String, x: Float, y: Float, r: Float, rnd: Random) {
        val u = r / 14f
        paint.style = Paint.Style.FILL
        c.save()
        c.rotate(rnd.nextFloat() * 180f, x, y)
        when (nama) {
            "Meses" -> {
                paint.color = mesesWarna[rnd.nextInt(mesesWarna.size)]
                c.drawRoundRect(x - u, y - u * 0.35f, x + u, y + u * 0.35f, u * 0.3f, u * 0.3f, paint)
            }
            "Choco chips" -> {
                paint.color = Color.parseColor("#4A1B0C")
                c.drawCircle(x, y, u * 0.6f, paint)
            }
            "Keju parut" -> {
                paint.color = Color.parseColor("#FAC775")
                c.drawRect(x - u * 0.9f, y - u * 0.25f, x + u * 0.9f, y + u * 0.25f, paint)
            }
            "Crumble" -> {
                paint.color = Color.parseColor(if (rnd.nextBoolean()) "#D7B999" else "#B8895C")
                c.drawRect(x - u * 0.5f, y - u * 0.5f, x + u * 0.5f, y + u * 0.5f, paint)
            }
            "Oreo" -> {
                paint.color = Color.parseColor("#2C2C2A")
                c.drawCircle(x, y, u * 1.1f, paint)
                paint.color = Color.WHITE
                c.drawCircle(x, y, u * 0.55f, paint)
            }
            "Almond" -> {
                paint.color = Color.parseColor("#C9A27E")
                c.drawOval(x - u * 0.9f, y - u * 0.5f, x + u * 0.9f, y + u * 0.5f, paint)
            }
            "Kelapa parut" -> {
                paint.color = Color.WHITE
                c.drawCircle(x, y, u * 0.4f, paint)
            }
            "Marshmallow" -> {
                paint.color = if (rnd.nextBoolean()) Color.WHITE else Color.parseColor("#F4C0D1")
                c.drawRoundRect(x - u * 0.8f, y - u * 0.8f, x + u * 0.8f, y + u * 0.8f, u * 0.3f, u * 0.3f, paint)
            }
            "Karamel" -> {
                paint.color = Color.parseColor("#BA7517")
                c.drawRoundRect(x - u * 1.6f, y - u * 0.2f, x + u * 1.6f, y + u * 0.2f, u * 0.2f, u * 0.2f, paint)
            }
            "Wafer" -> {
                paint.color = Color.parseColor("#B8895C")
                c.drawRect(x - u * 1.3f, y - u * 0.5f, x + u * 1.3f, y + u * 0.5f, paint)
            }
            "Selai stroberi" -> {
                paint.color = Color.parseColor("#E24B4A")
                c.drawCircle(x, y, u * 0.9f, paint)
            }
            "Boba mini" -> {
                paint.color = Color.parseColor("#2C2C2A")
                c.drawCircle(x, y, u * 0.7f, paint)
            }
        }
        c.restore()
    }

    private fun gambarWajah(c: Canvas, cx: Float, cy: Float, r: Float, lubang: Float) {
        val y = cy + lubang + (r - lubang) * 0.55f
        val dx = r * 0.22f

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#4A1B0C")
        c.drawCircle(cx - dx, y, r * 0.045f, paint)
        c.drawCircle(cx + dx, y, r * 0.045f, paint)

        paint.color = Color.parseColor("#F09595")
        paint.alpha = 180
        c.drawOval(cx - dx * 1.9f, y + r * 0.05f, cx - dx * 1.3f, y + r * 0.11f, paint)
        c.drawOval(cx + dx * 1.3f, y + r * 0.05f, cx + dx * 1.9f, y + r * 0.11f, paint)

        paint.alpha = 255
        paint.color = Color.parseColor("#4A1B0C")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = r * 0.035f
        paint.strokeCap = Paint.Cap.ROUND
        c.drawArc(cx - r * 0.08f, y - r * 0.03f, cx + r * 0.08f, y + r * 0.09f, 20f, 140f, false, paint)
        paint.style = Paint.Style.FILL
    }
}