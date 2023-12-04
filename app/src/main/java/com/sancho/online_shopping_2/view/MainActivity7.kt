package com.sancho.online_shopping_2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.sancho.online_shopping_2.databinding.ActivityMain7Binding
import com.sancho.online_shopping_2.view.adapters.FavoriteAdapter
import com.sancho.online_shopping_2.viewmodel.ProductViewModel
import java.util.Calendar

class   MainActivity7 : AppCompatActivity() {
    lateinit var binding: ActivityMain7Binding
    lateinit var productViewModel: ProductViewModel
    lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var username=intent.getStringExtra("username")
        var surname=intent.getStringExtra("surname")
        var phone=intent.getStringExtra("phone")
        var adress=intent.getStringExtra("adress")
        productViewModel=ViewModelProvider(this@MainActivity7).get(ProductViewModel::class.java)

        productViewModel.readallorderss(username!!).observe(this@MainActivity7, {
            binding.textviewusername7.text=username
            binding.textviewordername.text=""
            var totalcost: Int = 0
            for (i in 0 until it.size) {
                binding.textviewordername.append("${it.get(i).name}\n ${it.get(i).price}"+" $\n")
                totalcost = totalcost + it.get(i).price!!.toInt()
            }
            binding.textviewordername.append("\nИтоговая Сумма $totalcost"+" $")
            binding.apply {
                recyclerviewfavorite.layoutManager = LinearLayoutManager(this@MainActivity7)
                favoriteAdapter = FavoriteAdapter(this@MainActivity7, it)
                recyclerviewfavorite.adapter = favoriteAdapter
            }
        })

            binding.buttonorder.setOnClickListener {
                val calendar=Calendar.getInstance()
                var day=calendar.get(Calendar.DAY_OF_MONTH)
                var month=calendar.get(Calendar.MONTH)+1
                var year=calendar.get(Calendar.YEAR)
                var datatime="$day/$month/$year"
                productViewModel.buy(
                    orders = binding.textviewordername.text.toString(),
                    username = username,
                    surname = surname!!,
                    phone = phone!!,
                    address = adress!!,
                    dataime = datatime
                )
            }

            productViewModel.buysucces().observe(this@MainActivity7,{
                if (it){
                    Toast.makeText(this@MainActivity7,"Успешно!", Toast.LENGTH_SHORT).show()
                }
            })

        binding.backm7.setOnClickListener {
            finish()
        }

        }

    }