package com.example.andeestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Campos

class CamposAdapter(private val camposList:List<Campos>, private val onClickListener:(Campos) -> Unit): RecyclerView.Adapter<CamposViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CamposViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CamposViewHolder(layoutInflater.inflate(R.layout.item_campos, parent, false))
    }

    override fun onBindViewHolder(holder: CamposViewHolder, position: Int) {
        val item = camposList[position]
        holder.render(item, onClickListener)

    }

    override fun getItemCount(): Int = camposList.size

}