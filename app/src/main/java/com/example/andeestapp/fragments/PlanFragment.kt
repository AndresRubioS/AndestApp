package com.example.andeestapp.fragments

import android.content.ContentValues
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.CampoProvider
import com.example.andeestapp.CamposAdapter
import com.example.andeestapp.R
import com.example.andeestapp.databinding.FragmentPlanBinding
import com.example.andeestapp.galery.GaleriaActivity
import com.example.data.Campos
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class PlanFragment : Fragment() {
    private var _binding: FragmentPlanBinding? = null
    private val db = Firebase.firestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = PlanFragment()
    }

    private lateinit var viewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        initRecyclerView()
        configSwip()
        addCampo()
        initRecyclerViewChekedTrue()
        initRecyclerViewChekedFalse()
    }

    private fun initRecyclerViewChekedFalse() {
        binding.button.setOnClickListener {
            CampoProvider.camposList.clear()
            val recyclerView = binding.rvCampos
            recyclerView.layoutManager = LinearLayoutManager(context)
            val itemToched = ItemTouchHelper(simpleCallback)
            itemToched.attachToRecyclerView(recyclerView)
            db.collection("users")
                .whereEqualTo("checked",false)
                // .orderBy("fecha")
                .get()
                .addOnSuccessListener { documents->


                    CampoProvider.camposList.addAll(documents.toObjects(Campos::class.java))
                    Log.d("fotosImagen",CampoProvider.camposList.toString())
                    recyclerView.adapter = CamposAdapter(CampoProvider.camposList,{onItemSelected(it)})



                }

        }
    }

    private fun initRecyclerViewChekedTrue() {

        binding.button2.setOnClickListener {
            CampoProvider.camposList.clear()
            val recyclerView = binding.rvCampos
            recyclerView.layoutManager = LinearLayoutManager(context)
            val itemToched = ItemTouchHelper(simpleCallback)
            itemToched.attachToRecyclerView(recyclerView)
            db.collection("users")
                .whereEqualTo("checked",true)
                // .orderBy("fecha")
                .get()
                .addOnSuccessListener { documents->


                    CampoProvider.camposList.addAll(documents.toObjects(Campos::class.java))
                    Log.d("fotosImagen",CampoProvider.camposList.toString())
                    recyclerView.adapter = CamposAdapter(CampoProvider.camposList,{onItemSelected(it)})



                }

        }
    }

    private fun configSwip() {
        binding.swip.setOnRefreshListener {


            Handler(Looper.getMainLooper()).postDelayed({
                CampoProvider.camposList.clear()
                val recyclerView = binding.rvCampos

                recyclerView.adapter?.notifyDataSetChanged()
                initRecyclerView()

                binding.swip.isRefreshing = false

            },1000)
        }
    }

    private fun addCampo() {
        binding.btnAddCampo.setOnClickListener{
            val builder = AlertDialog.Builder(context!!)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.layout_add_campo,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_add_campo)
            val calendario = dialogLayout.findViewById<CalendarView>(R.id.calendarView)
            val fecha = dialogLayout.findViewById<TextView>(R.id.fecha)
            calendario.setOnDateChangeListener{view,year,month,dayOfMonth ->

                val msg = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

               // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                fecha.setText(msg)


            }



            with(builder){
                setTitle("Que harán los maracos culiaos?")
                setPositiveButton("OK"){dialog, which->
                    val resultado = editText.text.toString()
                    if (resultado.isNotEmpty()){

                        val fechaMSG =  fecha.text



                        CampoProvider.camposList.add(Campos(resultado,false,fechaMSG.toString()))
                        db.collection("users").document(resultado)
                            .set(Campos(resultado,false,fechaMSG.toString()))
                            .addOnSuccessListener { documentReference ->

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

    private fun initRecyclerView2() {
        val recyclerView = binding.rvCampos
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemToched = ItemTouchHelper(simpleCallback)
        itemToched.attachToRecyclerView(recyclerView)
        db.collection("us")
            //  .whereEqualTo("checked",false)
            // .orderBy("fecha")
            .get()
            .addOnSuccessListener { documents->


                CampoProvider.camposList.addAll(documents.toObjects(Campos::class.java))
                Log.d("fotosImagen",CampoProvider.camposList.toString())
                recyclerView.adapter = CamposAdapter(CampoProvider.camposList,{onItemSelected(it)})



            }

    }
    private fun initRecyclerView() {
        CampoProvider.camposList.clear()
        val recyclerView = binding.rvCampos
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemToched = ItemTouchHelper(simpleCallback)
        itemToched.attachToRecyclerView(recyclerView)
        db.collection("users")
            .whereEqualTo("checked",false)
            //.orderBy("fecha")
            .get()
            .addOnSuccessListener { documents->


                CampoProvider.camposList.addAll(documents.toObjects(Campos::class.java))
                Log.d("fotosImagen",CampoProvider.camposList.toString())
                recyclerView.adapter = CamposAdapter(CampoProvider.camposList,{onItemSelected(it)})
                CampoProvider.camposList.sortBy { it.fecha }



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
        val intent = Intent(context, GaleriaActivity::class.java)
        intent.putExtra("campo", it)
        startActivity(intent)
    }
}
