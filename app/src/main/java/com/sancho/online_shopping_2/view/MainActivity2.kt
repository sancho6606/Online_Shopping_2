package com.sancho.online_shopping_2.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMain2Binding
import com.sancho.online_shopping_2.viewmodel.CategoryViewModel

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    var imageuri:Uri?=null
    lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryViewModel=ViewModelProvider(this@MainActivity2).get(CategoryViewModel::class.java)

        binding.apply {
            imageviewopengallery.setOnClickListener {
                openFileChooser()
            }
            buttonaddcategories.setOnClickListener {
                if (imageuri!=null){
                    categoryViewModel.uploadnewcategory(edittextimagename.text.toString(),imageuri!!)
                }else{
                    Toast.makeText(this@MainActivity2,"Установите фото для категории", Toast.LENGTH_SHORT).show()
                }

            }
            backm2.setOnClickListener {
                finish()
            }
        }

        //progreassbarshowhide
        categoryViewModel.uploadsucces().observe(this@MainActivity2,{
            if (it){
                showprogressbar()
            }else{
                hideprogressbar()
                Toast.makeText(this@MainActivity2,"Успешно!", Toast.LENGTH_SHORT).show()
            }
        })
        categoryViewModel.uploadprogress().observe(this@MainActivity2,{
            binding.apply {
                progressbaruploadimage.progress=it.toInt()
                textviewprogress.text="${it.toInt()} %"
            }
        })
        //progressbarshowhide

    }

    //Show progressbar hide progressbar
    fun showprogressbar(){
        binding.apply {
            progressbaruploadimage.visibility=View.VISIBLE
            textviewprogress.visibility=View.VISIBLE
        }
    }
    fun hideprogressbar(){
        binding.apply {
            progressbaruploadimage.visibility=View.GONE
            textviewprogress.visibility=View.GONE
        }
    }
    //Show progressbar hide progressbar

    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageviewopengallery.setImageURI(uri)
        if (uri!=null){
            imageuri=uri
            binding.buttonaddcategories.isEnabled=true
        }
    }

}