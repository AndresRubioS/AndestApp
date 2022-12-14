package com.example.andeestapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Adapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ActivityMainBinding

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


        initRecyclerView()
        configSwip()
        addCampo()
      //  todoAdapter = CamposAdapter(mutableListOf())

        val user = hashMapOf(
            "first" to "Ada1",
            "last" to "Lovelace",
            "born" to 1815
        )

// Add a new document with a generated ID



        //prueba en github
        //hh


    }

    private fun configSwip() {
        binding.swip.setOnRefreshListener {


            Handler(Looper.getMainLooper()).postDelayed({
                binding.swip.isRefreshing = false

            },1000)
        }
    }

    private fun addCampo() {
        binding.btnAddCampo.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.layout_add_campo,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_add_campo)
            with(builder){
                setTitle("pon")
                setPositiveButton("OK"){dialog, which->
                    val resultado = editText.text.toString()
                    if (resultado.isNotEmpty()){
                        CampoProvider.camposList.add(Campos(resultado))
                        db.collection("users")
                            .add(Campos(resultado))
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }



                }
                setNegativeButton("cancel"){dialog,which->
                    Log.d("cancela","cancelas")

                }
                setView(dialogLayout)
                show()
            }





        }
    }
    private fun initRecyclerView() {
        val recyclerView = binding.rvCampos
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemToched = ItemTouchHelper(simpleCallback)
        itemToched.attachToRecyclerView(recyclerView)
        db.collection("users")
            .get()
            .addOnSuccessListener { documents->
                CampoProvider.camposList.addAll(documents.toObjects(Campos::class.java))
                Log.d("fotosImagen",CampoProvider.camposList.toString())
                recyclerView.adapter = CamposAdapter(CampoProvider.camposList,{onItemSelected(it)})


            }

    }
    val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,0){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ):Boolean{
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(CampoProvider.camposList,fromPosition,toPosition)
            recyclerView.adapter?.notifyItemMoved(fromPosition,toPosition)
            return false

        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }


//    private fun initRecyclerView() {
//        val manager =  LinearLayoutManager(this)
//
//        val decoration = DividerItemDecoration(this, manager.orientation)
//
//        val recyclerView = binding.rvCampos
//
//        recyclerView.layoutManager = manager
//        recyclerView.adapter = CamposAdapter(CampoProvider.camposList)
//        // recyclerView.addItemDecoration(decoration)
//    }

    private fun onItemSelected(it: Campos) {
        val intent = Intent(this, GaleriaActivity::class.java)
        intent.putExtra("campo", it)
        startActivity(intent)
    }



}
