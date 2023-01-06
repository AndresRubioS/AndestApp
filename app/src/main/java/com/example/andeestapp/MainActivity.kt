package com.example.andeestapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ActivityMainBinding
import com.example.andeestapp.fragments.ImportanteFragment
import com.example.andeestapp.fragments.PelisFragment
import com.example.andeestapp.fragments.PlanFragment
import com.example.andeestapp.fragments.PlanesFragment


import com.example.andeestapp.galery.GaleriaActivity
import com.example.andeestapp.galery.GaleriaViewHolder


import com.example.data.Campos
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: CamposAdapter
    private lateinit var binding: ActivityMainBinding

    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  todoAdapter = CamposAdapter(mutableListOf())


        setUpTabs()



    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PlanFragment(),"Planes")
        adapter.addFragment(PelisFragment(),"Pelis")
        adapter.addFragment(ImportanteFragment(),"Documentos importantes")

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
       // binding.tabs.getTabAt(0)!!.setIcon



    }





}
