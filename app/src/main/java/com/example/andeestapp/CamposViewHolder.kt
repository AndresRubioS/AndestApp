package com.example.andeestapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ItemCamposBinding
import com.example.data.Campos

class CamposViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val binding = ItemCamposBinding.bind(view)
    var cantidad = 0



    fun render(CamposModel: Campos, onClickListener:(Campos) -> Unit){
        binding.textView.text = CamposModel.Nombre


        itemView.setOnClickListener { onClickListener(CamposModel) }




    }






}