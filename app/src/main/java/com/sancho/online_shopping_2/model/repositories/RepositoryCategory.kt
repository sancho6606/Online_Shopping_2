package com.sancho.online_shopping_2.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sancho.online_shopping_2.model.CategoryModel
import com.sancho.online_shopping_2.utitlits.Constants.CATEGORIES

class RepositoryCategory constructor(
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(CATEGORIES),
    val storageReference: StorageReference = FirebaseStorage.getInstance().getReference(CATEGORIES)
) {

    var livedatasucces=MutableLiveData<Boolean>()
    var livedataprogress=MutableLiveData<Double>()

    val arrayList=ArrayList<CategoryModel>()
    val livedataallcategories=MutableLiveData<ArrayList<CategoryModel>>()

    fun uploadcategory(name:String,uri: Uri){
        if (uri!=null){
            succes(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener { filereference.downloadUrl.addOnSuccessListener {
                    var pushkey=databaseReference.push().key.toString()
                    val category=CategoryModel(name,it.toString(),pushkey)
                    //Realtime Database
                    databaseReference.child(pushkey).setValue(category).addOnCompleteListener {
                        if (it.isSuccessful){
                            succes(false)
                        }
                    }

                } }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    livedataprogress.value=progress
                }
        }

    }

    fun readalldata():MutableLiveData<ArrayList<CategoryModel>>{
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val category=datasnapshot.getValue(CategoryModel::class.java)
                    arrayList.add(category!!)
                }
                livedataallcategories.value=arrayList

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedataallcategories
    }

    fun succes(boolean: Boolean){
        livedatasucces.value=boolean
    }

}