package com.example.andeestapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.R
import com.example.data.Campos


class PeliAdapter(private val camposList:MutableList<Campos>): RecyclerView.Adapter<PeliViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeliViewHolder(layoutInflater.inflate(R.layout.item_pelis, parent, false))
    }

    override fun onBindViewHolder(holder: PeliViewHolder, position: Int) {
        val item = camposList[position]
        holder.render(item)


    }

    override fun getItemCount(): Int = camposList.size

}