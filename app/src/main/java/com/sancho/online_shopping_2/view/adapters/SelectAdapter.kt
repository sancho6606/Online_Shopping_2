package com.sancho.online_shopping_2.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sancho.online_shopping_2.R

class SelectAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<Uri>,
):RecyclerView.Adapter<SelectAdapter.SelectViewHolder>() {

    class SelectViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val imageview:ImageView=itemview.findViewById(R.id.imageviewselectedproduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recyclerview_selectimages,parent,false)
        return SelectViewHolder(view)
    }
    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.imageview.setImageURI(arrayList.get(position))
    }
    override fun getItemCount(): Int = arrayList.size


}