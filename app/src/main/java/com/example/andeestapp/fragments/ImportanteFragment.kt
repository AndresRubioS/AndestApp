package com.example.andeestapp.fragments

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.R
import com.example.andeestapp.databinding.FragmentImportanteBinding
import com.example.andeestapp.galery.GaleriaAdapter
import com.example.andeestapp.galery.GaleriaDetallesActivity
import com.example.data.Campos
import com.example.data.ListaProvider
import com.example.data.Listas
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.HashMap

class ImportanteFragment : Fragment() {

    private var _binding: FragmentImportanteBinding? = null
    val db = Firebase.database
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val REQUEST_CODE_SPEECH_INPUT = 18
    private val File = 1

    private val fileResult = 1
    val storage = Firebase.storage
    val listRef = storage.reference.child("User")

    private val FOTOS = "users"
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var photoArrayList : ArrayList<Listas>
    private lateinit var photoRecyclingView: RecyclerView

    companion object {
        fun newInstance() = ImportanteFragment()
    }

    private lateinit var viewModel: ImportanteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImportanteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImportanteViewModel::class.java)
        val audio = binding.btnAudio
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(FOTOS)




        initRecyclerView()



        audio.setOnClickListener {
            //fileUpload()
            fileManager()


        }



    }


    private fun initRecyclerView() {

        photoRecyclingView = binding.rvGaleria
        photoRecyclingView.layoutManager = GridLayoutManager(context, 3)

        photoArrayList = arrayListOf()

        getPhotoData()

        // photoRecyclingView.adapter = GaleriaAdapter(photoArrayList)




    }

    private fun getPhotoData() {



            mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Importante")

        mDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val photo = userSnapshot.getValue(Listas::class.java)
                          //ListaProvider.listaList.clear()

                        photoArrayList.add(photo!!)


                    }
                    photoRecyclingView.adapter = GaleriaAdapter(photoArrayList, {onItemSelected(it)})

                }
            }

            override fun onCancelled(error: DatabaseError) {


            }

        })
    }


    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        intent.type = "*/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                }else {
                    photoArrayList.clear()
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }

            }
        }
    }

    private fun fileUpload(mUri: Uri) {
        val key = mDatabaseReference.push().key!!

        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Importante")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["ListaNombre"] = java.lang.String.valueOf(uri)

//                it.storage.downloadUrl.addOnSuccessListener {
//                    savePhoto(key,it.toString())
//                }


                    val myRef = db.getReference("Importante")

                    myRef.child(myRef.push().key.toString()).setValue(hashMap)







                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
        }
    }
    private fun savePhoto(key: String, url:String){
        val imagen = Listas(ListaNombre = url)
        mDatabaseReference.child(key).setValue(imagen)

    }
    private fun onItemSelected(it: Listas) {
        val intent = Intent(context, GaleriaDetallesActivity::class.java)
        intent.putExtra("imagen", it)
        startActivity(intent)
    }

}