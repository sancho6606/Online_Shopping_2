package com.sancho.online_shopping_2.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.model.OrderModel

class OrderAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<OrderModel>
):RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {


    class OrderViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
        val textView:TextView=itemview.findViewById(R.id.textvieworderread)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recyclerview_item_order,parent,false)
        return OrderViewHolder(view)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.textView.text="${arrayList.get(position).datatime}\n${arrayList.get(position).orders}"
    }
    override fun getItemCount(): Int = arrayList.size


}