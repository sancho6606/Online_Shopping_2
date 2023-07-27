package com.sancho.online_shopping_2.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMainBinding
import com.sancho.online_shopping_2.model.CategoryModel
import com.sancho.online_shopping_2.model.ProductModel
import com.sancho.online_shopping_2.utitlits.Constants
import com.sancho.online_shopping_2.utitlits.Constants.IMAGES
import com.sancho.online_shopping_2.utitlits.Constants.USERNAME
import com.sancho.online_shopping_2.utitlits.Constants.USER_INFORMATION
import com.sancho.online_shopping_2.view.adapters.AllProductsAdapter
import com.sancho.online_shopping_2.view.adapters.CategoryAdapter
import com.sancho.online_shopping_2.viewmodel.CategoryViewModel
import com.sancho.online_shopping_2.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productViewModel: ProductViewModel
    lateinit var allProductsAdapter: AllProductsAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReferenceproductimages: DatabaseReference
    var databaseReferenceorder: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS)
    var arraylistallorder=ArrayList<ProductModel>()
    var useruid:String?=null
    var admin=false

    //Search
    var arraylistallproducts=ArrayList<ProductModel>()
    //
    var surnamee:String=""
    var phonee:String=""
    var adress:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        USERNAME=""
        categoryViewModel=ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)
        productViewModel=ViewModelProvider(this@MainActivity).get(ProductViewModel::class.java)
        useruid=intent.getStringExtra("uid")
        if (useruid=="vTTfTcVNktSwzAx2CfMO9cfMKcg2"){
            admin=true
            Toast.makeText(this@MainActivity,"Привет админ",Toast.LENGTH_SHORT).show()
            binding.imageviewopenac2.visibility=View.VISIBLE
        }
        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION).child(useruid!!)
        databaseReferenceproductimages=FirebaseDatabase.getInstance().getReference().child(IMAGES)

        val view=binding.navigationview.getHeaderView(0)
        val textViewusername:TextView=view.findViewById(R.id.textviewheaderusername)
        val textViewemail:TextView=view.findViewById(R.id.textviewheaderemail)
        val textViewphone:TextView=view.findViewById(R.id.textviewheaderphone)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var name=snapshot.child("name").getValue().toString()
                var surname=snapshot.child("surname").getValue().toString()
                var phone=snapshot.child("phone").getValue().toString()
                var address=snapshot.child("address").getValue().toString()
                var email=snapshot.child("email").getValue().toString()
                var password=snapshot.child("password").getValue().toString()
                USERNAME=name
                surnamee=surname
                phonee=phone
                adress=address
                binding.textviewusername.text=name
                textViewusername.text="$name $surname"
                textViewemail.text=email
                textViewphone.text=phone

                //ORDER BADGE
                databaseReferenceorder.child(name).addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        arraylistallorder.clear()
                        for (datasnapshot:DataSnapshot in snapshot.children){
                            var productModel=datasnapshot.getValue(ProductModel::class.java)
                            arraylistallorder.add(productModel!!)
                        }
                        if (arraylistallorder.size>0){
                            binding.badgecounter.visibility=View.VISIBLE
                            binding.badgecounter.text="${arraylistallorder.size}"
                        }else{
                            binding.badgecounter.visibility=View.INVISIBLE
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
                //ORDER

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }
            recyclerviewcategory.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
            categoryViewModel.readallcategory().observe(this@MainActivity,{
                categoryAdapter=CategoryAdapter(this@MainActivity,it)
                recyclerviewcategory.adapter=categoryAdapter
            })

            recyclerviewallproducts.layoutManager=GridLayoutManager(this@MainActivity,2)

            productViewModel.readallproducts().observe(this@MainActivity,{
                arraylistallproducts=it
                it.shuffle()
                allProductsAdapter= AllProductsAdapter(this@MainActivity,it)
                recyclerviewallproducts.adapter=allProductsAdapter
            })

            imageviewopennavigation.setOnClickListener {
                drawerlayout.openDrawer(Gravity.LEFT)
            }

            navigationview.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.item1->{
                        val intent=Intent(this@MainActivity,MainActivity8::class.java)
                        intent.putExtra("phone",phonee)
                        startActivity(intent)
                    }
                    R.id.item2->{
                        save(null)
                        finish()
                    }
                }
                return@setNavigationItemSelectedListener true
            }

            imagevieworder.setOnClickListener {
                if (useruid=="vTTfTcVNktSwzAx2CfMO9cfMKcg2"){
                    val intent=Intent(this@MainActivity,MainActivity9::class.java)
                    startActivity(intent)
                }else{
                    val intent=Intent(this@MainActivity,MainActivity7::class.java)
                    intent.putExtra("username", USERNAME)
                    intent.putExtra("surname", surnamee)
                    intent.putExtra("phone",phonee)
                    intent.putExtra("adress", adress)
                    startActivity(intent)
                }

            }
            edittextsearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filter(p0.toString())
                }
            })
        }

    }

    fun categorychanged(categoryname:String){
        productViewModel.readeverycategory(categoryname).observe(this@MainActivity,{
            allProductsAdapter=AllProductsAdapter(this@MainActivity,it)
                binding.recyclerviewallproducts.adapter=allProductsAdapter
        })
    }

    fun save(text: String?) {
        val editor = getSharedPreferences("Pr", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("pr", text)
        editor.commit()
    }

    //Search
    fun filter(text: String) {
        val searcharraylist = ArrayList<ProductModel>()

        //Products
        for (item: ProductModel in arraylistallproducts) {
            if (item.name?.toLowerCase()!!.contains(text.toLowerCase())) {
                searcharraylist.add(item)
            }
        }
        allProductsAdapter.filteredList(searcharraylist)

//        //Categories
//        val searcharraylist2 = ArrayList<CategoryModel>()
//        for (item: CategoryModel in arrayListcategories) {
//            if (item.name?.toLowerCase()!!.contains(text.toLowerCase())) {
//                searcharraylist2.add(item)
//            }
//        }
//        categoryAdapter.filteredList(searcharraylist2)


    }

    override fun onBackPressed(){
        productViewModel.readallproducts().observe(this@MainActivity, {
            arraylistallproducts = it
            it.shuffle()
            allProductsAdapter = AllProductsAdapter(this@MainActivity, it)
            binding.recyclerviewallproducts.adapter = allProductsAdapter
        })
    }



}