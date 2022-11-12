package com.example.andeestapp.galery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.R
import com.example.data.Listas


class GaleriaAdapter(private val camposList:MutableList<Listas>, private val onClickListener:(Listas) -> Unit): RecyclerView.Adapter<GaleriaViewHolder>() {




        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return GaleriaViewHolder(layoutInflater.inflate(R.layout.item_galeria, parent, false))
        }

        override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
            val item = camposList[position]
            holder.render(item,onClickListener)


        }

        override fun getItemCount(): Int = camposList.size

    }
