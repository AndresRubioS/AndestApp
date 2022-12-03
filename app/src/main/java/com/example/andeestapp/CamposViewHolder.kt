package com.example.andeestapp

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ItemCamposBinding
import com.example.data.Campos

class CamposViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val binding = ItemCamposBinding.bind(view)
    var cantidad = 0

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun render(CamposModel: Campos, onClickListener: (Campos) -> Unit){
        binding.textView.text = CamposModel.Nombre
        binding.fechaTV.text = CamposModel.fecha
        binding.checkBox.isChecked = CamposModel.isChecked
        toggleStrikeThrough(binding.textView, CamposModel.isChecked)
        itemView.setOnClickListener { onClickListener(CamposModel) }
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(binding.textView, isChecked)
            CamposModel.isChecked = !CamposModel.isChecked
        }








    }






}