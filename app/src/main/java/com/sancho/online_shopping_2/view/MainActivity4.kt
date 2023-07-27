package com.sancho.online_shopping_2.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMain4Binding
import com.sancho.online_shopping_2.model.ImageModel
import com.sancho.online_shopping_2.model.ProductModel
import com.sancho.online_shopping_2.utitlits.Constants.IMAGES
import com.sancho.online_shopping_2.viewmodel.ProductViewModel

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding
    lateinit var databaseReference: DatabaseReference
    var arrayList=ArrayList<ImageModel>()
    val imageList = ArrayList<SlideModel>()
    lateinit var productViewModel:ProductViewModel
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val product:ProductModel=intent.getSerializableExtra("product") as ProductModel
            //Glide.with(this@MainActivity4).load(product!!.uri).into(imageviewproduct)
            var pushkey=product.pushkey
            databaseReference=FirebaseDatabase.getInstance().getReference().child(IMAGES).child(pushkey!!)
            textviewproductname.text="${product!!.name}"
            textviewproductdescription.text="${product!!.ddescription}"
            textviewproductprice.text="${product!!.price}"+" $"

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    arrayList.clear()
                    for (datasnapshot:DataSnapshot in snapshot.children){
                        val imageModel=datasnapshot.getValue(ImageModel::class.java)
                        arrayList.add(imageModel!!)
                    }

                    for (img in arrayList){
                        imageList.add(SlideModel(img.imageurl))
                    }
                    imageviewproduct.setImageList(imageList,ScaleTypes.CENTER_CROP)

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            //ORDER
            productViewModel=ViewModelProvider(this@MainActivity4).get(ProductViewModel::class.java)
            buttonaddcategories.setOnClickListener {
                productViewModel.addneworder(
                    name = product.name!!,
                    imguri = product.imguri!!,
                    price = product.price!!,
                    description = product.ddescription!!,
                    pushkey = product.pushkey!!
                )
                Toast.makeText(this@MainActivity4,"Успешно!", Toast.LENGTH_SHORT).show()
            }

            backm4.setOnClickListener {
                finish()
            }
        }
    }
}