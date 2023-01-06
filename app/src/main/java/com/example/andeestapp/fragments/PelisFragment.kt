package com.example.andeestapp.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andeestapp.CampoProvider
import com.example.andeestapp.R
import com.example.andeestapp.databinding.FragmentPelisBinding
import com.example.andeestapp.databinding.FragmentPlanBinding
import com.example.data.Campos
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PelisFragment : Fragment() {

    private var _binding: FragmentPelisBinding? = null
    private val db = Firebase.firestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PelisFragment()
    }

    private lateinit var viewModel: PelisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPelisBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PelisViewModel::class.java)



        CampoProvider.camposList.clear()
        initReciclingView()
        addCampo()



    }

    private fun addCampo() {
        binding.floatingActionButton.setOnClickListener{
            val builder = AlertDialog.Builder(context!!)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.add_peli,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_add_peli)
            val puntuacionET = dialogLayout.findViewById<EditText>(R.id.puntuacion)





            with(builder){
                setTitle("Que han visto los maracos culiaos?")
                setPositiveButton("OK"){dialog, which->
                    val resultado = editText.text.toString()
                    val puntuacion = puntuacionET.text.toString()

                    if (resultado.isNotEmpty() && puntuacion.isNotEmpty()){

                        CampoProvider.peliculas.add(Campos(resultado,false,"",puntuacion))
                        db.collection("Peliculas")
                            .add(Campos(resultado,false,"",puntuacion))
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
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

    private fun initReciclingView() {
        val recyclerView = binding.pelisRv
        recyclerView.layoutManager = LinearLayoutManager(context)

        CampoProvider.peliculas.clear()
        // recyclerView.adapter = WebAdapter(WebProvider.AndresList)
        db.collection("Peliculas")
            //  .whereEqualTo("checked",false)
            .orderBy("puntuacion",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents->


                CampoProvider.peliculas.addAll(documents.toObjects(Campos::class.java))


                recyclerView.adapter = PeliAdapter( CampoProvider.peliculas)

            }
    }

}