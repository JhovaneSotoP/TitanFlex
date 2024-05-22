package com.jade.titanflex

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.caverock.androidsvg.SVG
import com.google.gson.internal.bind.TypeAdapters.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.time.LocalDate
import java.time.Period


fun setImageViewResource(imageView: ImageView, drawableResId: Int) {
    imageView.setImageResource(drawableResId)
}

suspend fun combineImages(
    context: Context,
    imageView: ImageView,
    baseImageResId: Int,
    svgUrls: List<String>
) {
    val baseImageDrawable = context.resources.getDrawable(baseImageResId, null)
    val baseImageBitmap = baseImageDrawable.toBitmap().apply {
        setDensity(Bitmap.DENSITY_NONE) // Establecer densidad de píxeles a densidad estándar
    }

    val bitmaps = mutableListOf<Bitmap>()

    // Descargar y convertir las imágenes SVG a Bitmaps
    withContext(Dispatchers.IO) {
        for (url in svgUrls) {
            try {
                val svg = SVG.getFromInputStream(URL(url).openStream())
                val bitmap = Bitmap.createBitmap(
                    svg.documentWidth.toInt(),
                    svg.documentHeight.toInt(),
                    Bitmap.Config.ARGB_8888
                ).apply { setDensity(Bitmap.DENSITY_NONE) }
                val canvas = Canvas(bitmap)
                svg.renderToCanvas(canvas)
                bitmaps.add(bitmap)
            }catch(ex:Exception){
                println("${ex.message}")
            }
        }
    }

    // Combinar las imágenes
    val resultBitmap = Bitmap.createBitmap(
        baseImageBitmap.width,
        baseImageBitmap.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(baseImageBitmap, 0f, 0f, null)
    println("${baseImageBitmap.width} - ${resultBitmap.width}")
    println("${baseImageBitmap.height} - ${resultBitmap.height}")

    if (bitmaps.isNotEmpty()) {
        for (bitmap in bitmaps) {
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, baseImageBitmap.width, baseImageBitmap.height, true)
            // Ajustar la posición para centrar la imagen
            val centerX = (baseImageBitmap.width - scaledBitmap.width) / 2
            val centerY = (baseImageBitmap.height - scaledBitmap.height) / 2
            canvas.drawBitmap(scaledBitmap, centerX.toFloat(), centerY.toFloat(), null)
        }
    }

    // Mostrar la imagen resultante en el ImageView
    withContext(Dispatchers.Main) {
        imageView.setImageBitmap(resultBitmap)
    }
}

suspend fun crearManiqui(
    context: Context,
    image_a: ImageView,
    image_b: ImageView,
    prin: List<elementosMusculo>,
    sec: List<elementosMusculo>
){
    val frontales= mutableListOf<String>()
    val traseros= mutableListOf<String>()

    for (n in sec){
        if (n.pos==true){
            frontales.add(n.url)
        }else{
            traseros.add(n.url)
        }
    }
    for (n in prin){
        if (n.pos==true){
            frontales.add(n.url)
        }else{
            traseros.add(n.url)
        }
    }

    combineImages(context,image_a,R.drawable.front,frontales)
    combineImages(context,image_b,R.drawable.back,traseros)
}

data class elementosMusculo(val url:String,val pos:Boolean)
data class elementoGrafica(var x:Float,var y:Float)

data class IMCResult(
    val imc: Float,
    val rango: String
)
@RequiresApi(Build.VERSION_CODES.O)
fun calcularIMCyRangoPorEdad(
    peso: Float,
    altura: Float,
    genero: Int,
    diaNacimiento: Int,
    mesNacimiento: Int,
    añoNacimiento: Int
): IMCResult {
    // Convertir altura de centímetros a metros
    val alturaMetros = altura / 100.0

    // Calcular el IMC
    val imc = peso / (alturaMetros * alturaMetros).toFloat()

    // Calcular la edad
    val fechaNacimiento = LocalDate.of(añoNacimiento, mesNacimiento, diaNacimiento)
    val hoy = LocalDate.now()
    val edad = Period.between(fechaNacimiento, hoy).years

    // Determinar el rango del IMC según la edad
    val rango = when {
        edad < 18 -> {
            when {
                imc < 5 -> "Desnutrición"
                imc < 18.5 -> "Bajo peso"
                imc < 25 -> "Peso saludable"
                imc < 30 -> "Sobrepeso"
                else -> "Obesidad"
            }
        }
        else -> {
            when {
                imc < 18.5 -> "Bajo peso"
                imc < 24.9 -> "Peso normal"
                imc < 29.9 -> "Sobrepeso"
                else -> "Obesidad"
            }
        }
    }

    return IMCResult(imc, rango)
}