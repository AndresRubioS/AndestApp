package com.example.andeestapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andeestapp.databinding.ActivityAudioTexto2Binding

import com.example.data.Campos
import com.example.data.Listas
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class AudioTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioTexto2Binding
    private val REQUEST_CODE_SPEECH_INPUT = 18
    private val File = 1
    val db = Firebase.database
    val myRef = db.getReference("user")
    private val fileResult = 1
    val storage = Firebase.storage
    val listRef = storage.reference.child("User")

    private val FOTOS = "users"
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var photoArrayList : ArrayList<Listas>
    private lateinit var photoRecyclingView: RecyclerView


   






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_texto2)
        binding = ActivityAudioTexto2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val audio = binding.btnAudio
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(FOTOS)


        val campos = intent.getParcelableExtra<Campos>("campo")
        if(campos != null){
            val campo = binding.TituloPlan
            campo.setText(campos.Nombre)



        }
        initRecyclerView()



        audio.setOnClickListener {
            //fileUpload()
            fileManager()


        }
    }


    private fun initRecyclerView() {

        photoRecyclingView = binding.rvGaleria
        photoRecyclingView.layoutManager = GridLayoutManager(this, 3)

        photoArrayList = arrayListOf()
        getPhotoData()
      // photoRecyclingView.adapter = GaleriaAdapter(photoArrayList)




    }

    private fun getPhotoData() {
        val campos = intent.getParcelableExtra<Campos>("campo")

        if (campos != null) {
            mDatabaseReference = FirebaseDatabase.getInstance().reference.child(campos.Nombre)
        }
        mDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val photo = userSnapshot.getValue(Listas::class.java)
                      //  ListaProvider.listaList.add(photo!!)
                        photoArrayList.add(photo!!)

                    }
                    photoRecyclingView.adapter = GaleriaAdapter(photoArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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
            if (resultCode == RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                }else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }

            }
        }
    }

    private fun fileUpload(mUri: Uri) {
        val key = mDatabaseReference.push().key!!
        val campos = intent.getParcelableExtra<Campos>("campo")
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child(campos?.Nombre.toString())
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = java.util.HashMap<String, String>()
                hashMap["ListaNombre"] = java.lang.String.valueOf(uri)

//                it.storage.downloadUrl.addOnSuccessListener {
//                    savePhoto(key,it.toString())
//                }
                val campos = intent.getParcelableExtra<Campos>("campo")
                if(campos != null){
                    val myRef = db.getReference(campos.Nombre)
                    myRef.child(myRef.push().key.toString()).setValue(hashMap)


                }




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

}


