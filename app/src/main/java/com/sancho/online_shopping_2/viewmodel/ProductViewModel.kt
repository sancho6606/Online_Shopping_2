package com.sancho.online_shopping_2.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sancho.online_shopping_2.model.ImageModel
import com.sancho.online_shopping_2.model.OrderModel
import com.sancho.online_shopping_2.model.ProductModel
import com.sancho.online_shopping_2.model.repositories.RepositoryProduct

class ProductViewModel constructor(
    val repositoryProduct: RepositoryProduct= RepositoryProduct()
):ViewModel() {

    fun addnewproduct(
        categoryname:String,
        name:String,
        uri: Uri,
        price:String,
        description:String,
        arraylistimage:ArrayList<Uri>
    ){
        repositoryProduct.addproduct(categoryname, name, uri, price, description,arraylistimage)
    }

    fun readallproducts():MutableLiveData<ArrayList<ProductModel>>{
        return repositoryProduct.readallproductsfirebase()
    }

    fun readeverycategory(categoryname: String):MutableLiveData<ArrayList<ProductModel>>{
        return repositoryProduct.readeverycategory(categoryname)
    }

    fun uploadproductsucces(): MutableLiveData<Boolean> {
        return repositoryProduct.livedatasucces
    }
    fun uploadproductprogress(): MutableLiveData<Double> {
        return repositoryProduct.livedataprogress
    }

    fun productallimages(pushkey:String):MutableLiveData<ArrayList<ImageModel>>{
        return repositoryProduct.productallimages(pushkey)
    }

    fun addneworder(
        name:String,
        imguri:String,
        price:String,
        description: String,
        pushkey: String,
    ){
        repositoryProduct.neworder(
            name = name,
            imguri = imguri,
            price = price,
            description = description,
            pushkey = pushkey
        )
    }

    fun readallorderss(username:String):MutableLiveData<ArrayList<ProductModel>>{
        return repositoryProduct.readallorders(username)
    }

    //Buy
    fun buy(
        orders :String,
        username :String,
        surname :String,
        phone :String,
        address :String,
        dataime :String
    ){
        repositoryProduct.buyproduct(
            orders,
            username,
            surname,
            phone,
            address,
            dataime
        )
    }

    fun buysucces():MutableLiveData<Boolean>{
        return repositoryProduct.livedatabuy
    }
//Buy

}