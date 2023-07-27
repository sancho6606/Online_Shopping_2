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
import com.sancho.online_shopping_2.databinding.ActivityMain9Binding
import com.sancho.online_shopping_2.model.OrderModel
import com.sancho.online_shopping_2.utitlits.Constants.ADMINORDER
import com.sancho.online_shopping_2.view.adapters.OrderAdapter
import com.sancho.online_shopping_2.view.adapters.OrderAdapterAdmin

class MainActivity9 : AppCompatActivity() {
    lateinit var binding: ActivityMain9Binding
    val arrayList=ArrayList<OrderModel>()
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().getReference().child(ADMINORDER)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val orderModel=datasnapshot.getValue(OrderModel::class.java)
                    arrayList.add(orderModel!!)
                }
                    binding.recyclerviewadminorder.layoutManager= LinearLayoutManager(this@MainActivity9)
                    val orderAdapter=OrderAdapterAdmin(this@MainActivity9,arrayList)
                    binding.recyclerviewadminorder.adapter=orderAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.backm9.setOnClickListener {
            finish()
        }
    }
}