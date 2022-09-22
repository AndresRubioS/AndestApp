package com.example.andeestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andeestapp.databinding.ActivityMainBinding

import com.example.data.CampoProvider
import com.example.data.Campos

class MainActivity : AppCompatActivity() {
    private lateinit var campoProvider: CampoProvider
    val arPedidos = arrayListOf<Campos>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initRecyclerView()
        addCampo()


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
        val manager =  LinearLayoutManager(this)

        val decoration = DividerItemDecoration(this, manager.orientation)

        val recyclerView = binding.rvCampos

        recyclerView.layoutManager = manager
        recyclerView.adapter = CamposAdapter(CampoProvider.camposList, {onItemSelected(it)})
        // recyclerView.addItemDecoration(decoration)
    }

    private fun onItemSelected(it: Campos) {
       // val intent = Intent(this, AudioTextActivity::class.java)
        intent.putExtra("campo", it)
        startActivity(intent)
    }



}
