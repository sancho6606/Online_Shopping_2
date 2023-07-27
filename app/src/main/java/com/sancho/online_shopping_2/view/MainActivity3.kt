package com.sancho.online_shopping_2.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMain3Binding
import com.sancho.online_shopping_2.view.adapters.SelectAdapter
import com.sancho.online_shopping_2.viewmodel.ProductViewModel

class MainActivity3 : AppCompatActivity() {
    lateinit var binding:ActivityMain3Binding
    var categoryname:String?=null
    var categoryimage:String?=null
    var imageuri:Uri?=null
    lateinit var productViewModel: ProductViewModel
    var arraylistimage = ArrayList<Uri>()
    lateinit var selectAdapter:SelectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        productViewModel=ViewModelProvider(this@MainActivity3).get(ProductViewModel::class.java)
        categoryname=intent.getStringExtra("name")
        categoryimage=intent.getStringExtra("image")

        binding.apply {
            textviewcategorynametitle.text=categoryname
            Glide.with(this@MainActivity3).load(categoryimage).into(circleimageviewcategory)

            imageviewopengalleryproduct.setOnClickListener {
                openFileChooser()
            }

            buttonaddproduct.setOnClickListener {
                productViewModel.addnewproduct(
                    categoryname!!,
                    edittextproductname.text.toString(),
                    imageuri!!,
                    edittextproductprice.text.toString(),
                    edittextproductdescription.text.toString(),
                    arraylistimage
                )
            }

            productViewModel.uploadproductsucces().observe(this@MainActivity3,{
                if (it){
                    showprogress()
                }else{
                    hideprogress()
                    Toast.makeText(this@MainActivity3,"Успешно!", Toast.LENGTH_SHORT).show()
                }
            })

            productViewModel.uploadproductprogress().observe(this@MainActivity3,{
                textviewprogressproduct.text="${it.toInt()} %"
                progressbaruploadproduct.progress=it.toInt()
            })

            backm3.setOnClickListener {
               finish()
            }

        }

    }

    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageviewopengalleryproduct.setImageURI(uri)
        if (uri!=null){
            imageuri=uri
            arraylistimage.add(uri)
            binding.buttonaddproduct.isEnabled=true
            binding.recyclerviewselectimages.layoutManager=LinearLayoutManager(this@MainActivity3,RecyclerView.HORIZONTAL,false)
            selectAdapter=SelectAdapter(this@MainActivity3,arraylistimage)
            binding.recyclerviewselectimages.adapter=selectAdapter
        }
    }

    //show progress hide progress
    fun showprogress(){
        binding.apply {
            progressbaruploadproduct.visibility= View.VISIBLE
            textviewprogressproduct.visibility= View.VISIBLE
        }
    }
    fun hideprogress(){
        binding.apply {
            progressbaruploadproduct.visibility= View.GONE
            textviewprogressproduct.visibility= View.GONE
        }
    }

}