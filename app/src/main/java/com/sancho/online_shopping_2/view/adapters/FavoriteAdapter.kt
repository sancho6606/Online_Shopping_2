package com.sancho.online_shopping_2.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sancho.online_shopping_2.databinding.RecyclerviewItemFavoriteBinding
import com.sancho.online_shopping_2.model.ImageModel
import com.sancho.online_shopping_2.model.ProductModel
import com.sancho.online_shopping_2.utitlits.Constants.IMAGES
import com.sancho.online_shopping_2.utitlits.Constants.ORDERS
import com.sancho.online_shopping_2.utitlits.Constants.USERNAME

class FavoriteAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<ProductModel>
):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    val databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference().child(ORDERS).child(USERNAME)
//    var databaseReferenceimage:DatabaseReference=FirebaseDatabase.getInstance().getReference().child(IMAGES)
//    var arrayListimage=ArrayList<ImageModel>()
//    val imageList = ArrayList<SlideModel>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view=RecyclerviewItemFavoriteBinding.inflate(LayoutInflater.from(context),parent,false)
        return FavoriteAdapter.FavoriteViewHolder(view)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binding.apply {
            textviewfavoritename.text="${arrayList.get(position).name}"
           // Glide.with(context).load(arrayList.get(position).imguri).into(imageviewfavorite)
            textviewfavoritedescription.text="${arrayList.get(position).ddescription}"
            textviewfavoriteprice.text="${arrayList.get(position).price}"+" $"
            imageviewfavoritedelete.setOnClickListener{
                databaseReference.child(arrayList.get(position).pushkey.toString()).removeValue()
            }
        }

    }
    override fun getItemCount(): Int = arrayList.size

    class FavoriteViewHolder(val binding: RecyclerviewItemFavoriteBinding):RecyclerView.ViewHolder(binding.root)
}