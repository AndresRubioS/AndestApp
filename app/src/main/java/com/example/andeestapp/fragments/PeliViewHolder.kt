package com.example.andeestapp.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ItemPelisBinding
import com.example.data.Campos

class PeliViewHolder(view : View): RecyclerView.ViewHolder(view) {

    val binding = ItemPelisBinding.bind(view)

    fun render(CamposModel: Campos) {


        // binding.textView3.text = CamposModel.titulo
//        binding.webview.loadUrl(CamposModel.UrlWeb)
//        binding.webview.webViewClient = WebViewClient()

        val nombre = CamposModel.Nombre
        val peli = CamposModel.puntuacion.toString()

        binding.textView.setText("$nombre     $peli")






    }
}