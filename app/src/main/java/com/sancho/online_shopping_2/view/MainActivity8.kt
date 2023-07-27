package com.sancho.online_shopping_2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMain8Binding
import com.sancho.online_shopping_2.model.OrderModel
import com.sancho.online_shopping_2.utitlits.Constants.HISTORY
import com.sancho.online_shopping_2.view.adapters.OrderAdapter

class MainActivity8 : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: ActivityMain8Binding
    lateinit var orderAdapter: OrderAdapter
    val arrayList=ArrayList<OrderModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var phone=intent.getStringExtra("phone")
        databaseReference=FirebaseDatabase.getInstance().getReference().child(HISTORY).child(phone!!)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val orderModel=datasnapshot.getValue(OrderModel::class.java)
                    arrayList.add(orderModel!!)
                }
                binding.recyclervieworder.layoutManager=LinearLayoutManager(this@MainActivity8)
                val orderAdapter=OrderAdapter(this@MainActivity8,arrayList)
                binding.recyclervieworder.adapter=orderAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.backm8.setOnClickListener {
            finish()
        }

    }
}