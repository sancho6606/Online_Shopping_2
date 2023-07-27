package com.sancho.online_shopping_2.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sancho.online_shopping_2.databinding.RecyclerItemCategoryBinding
import com.sancho.online_shopping_2.model.CategoryModel
import com.sancho.online_shopping_2.view.MainActivity
import com.sancho.online_shopping_2.view.MainActivity3

class CategoryAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>,
):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    val mainActivity:MainActivity=context as MainActivity

    class CategoryViewHolder(val binding:RecyclerItemCategoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view=RecyclerItemCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            textviewcategorytext.text=arrayList.get(position).name
            Glide.with(context).load(arrayList.get(position).imageurl).into(imageviewcategoryimage)

            linearlayoutcategorylayout.setOnLongClickListener {
                val intent=Intent(context,MainActivity3::class.java)
                intent.putExtra("name",arrayList.get(position).name)
                intent.putExtra("image",arrayList.get(position).imageurl)
                if (mainActivity.admin){
                    context.startActivity(intent)
                }

                return@setOnLongClickListener true
            }

            linearlayoutcategorylayout.setOnClickListener {
                mainActivity.categorychanged(arrayList.get(position).name!!)
            }
        }
    }
    override fun getItemCount(): Int = arrayList.size



}