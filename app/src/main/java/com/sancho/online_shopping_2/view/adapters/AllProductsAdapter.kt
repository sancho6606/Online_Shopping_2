package com.sancho.online_shopping_2.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sancho.online_shopping_2.databinding.RecyclerviewItemallproductsBinding
import com.sancho.online_shopping_2.model.ProductModel
import com.sancho.online_shopping_2.view.MainActivity4

class AllProductsAdapter constructor(
    val context: Context,
    var arrayList: ArrayList<ProductModel>,
):RecyclerView.Adapter<AllProductsAdapter.AllProductsViewHolder>() {

    class AllProductsViewHolder(val binding:RecyclerviewItemallproductsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val view=RecyclerviewItemallproductsBinding.inflate(LayoutInflater.from(context),parent,false)
        return AllProductsViewHolder(view)
    }
    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        holder.binding.apply {
            textviewallproducts.text=arrayList.get(position).name
            Glide.with(context).load(arrayList.get(position).imguri).into(imageviewallproducts)

            linearlayoutproductlayout.setOnClickListener {
                val intent=Intent(context,MainActivity4::class.java)
                intent.putExtra("product",arrayList.get(position))
                context.startActivity(intent)
            }

        }
    }
    override fun getItemCount(): Int = arrayList.size

    fun filteredList(filteredList:ArrayList<ProductModel>){
        arrayList = filteredList
        notifyDataSetChanged()
    }

}