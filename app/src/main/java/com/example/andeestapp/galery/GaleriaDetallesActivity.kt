package com.example.andeestapp.galery

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.andeestapp.R
import com.example.andeestapp.databinding.ActivityGaleriaDetallesBinding
import com.example.data.Campos
import com.example.data.Listas
import com.squareup.picasso.Picasso

class GaleriaDetallesActivity : AppCompatActivity() {

    lateinit var image: ImageView
    private lateinit var binding: ActivityGaleriaDetallesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria_detalles)
        binding = ActivityGaleriaDetallesBinding.inflate(layoutInflater)

        val campos = intent.getParcelableExtra<Listas>("imagen")
        if(campos != null){

            Log.d("Imagendetalle",campos.ListaNombre)
            image = findViewById(R.id.imageView2)
           Glide.with(this).load(campos.ListaNombre).into(image)
           //Picasso.get().load(campos.ListaNombre).into(image)



        }

    }
}