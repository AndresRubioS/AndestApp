package com.example.andeestapp

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andeestapp.databinding.ItemCamposBinding
import com.example.andeestapp.databinding.ItemGaleriaBinding
import com.example.data.Campos
import com.example.data.Listas


class GaleriaViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val binding = ItemGaleriaBinding.bind(view)


    fun render(CamposModel: Listas) {

       // binding.textView3.text = CamposModel.titulo
        Glide.with(binding.imageView.context).load(CamposModel.ListaNombre).into(binding.imageView)


    }
}
