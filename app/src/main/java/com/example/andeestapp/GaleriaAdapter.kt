package com.example.andeestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Campos
import com.example.data.Listas


class GaleriaAdapter(private val camposList:MutableList<Listas>): RecyclerView.Adapter<GaleriaViewHolder>() {




        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return GaleriaViewHolder(layoutInflater.inflate(R.layout.item_galeria, parent, false))
        }

        override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
            val item = camposList[position]
            holder.render(item)


        }

        override fun getItemCount(): Int = camposList.size

    }
