package com.sancho.online_shopping_2.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMainActivity5LoginBinding

class MainActivity5Login : AppCompatActivity() {
    lateinit var binding:ActivityMainActivity5LoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    var uid:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainActivity5LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        load()
        binding.apply {
            textviewlogregister.setOnClickListener {
                startActivity(Intent(this@MainActivity5Login,MainActivity6Register::class.java))
            }
            buttonlogin.setOnClickListener {
                progressbarlogin.visibility=View.VISIBLE
                firebaseAuth.signInWithEmailAndPassword(edittextloglogin.text.toString(),edittextlogpassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        uid=firebaseAuth.currentUser!!.uid
                        val intent=Intent(this@MainActivity5Login,MainActivity::class.java)
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                        progressbarlogin.visibility=View.GONE
                        save(uid)
                        Toast.makeText(this@MainActivity5Login,"Успешно!", Toast.LENGTH_SHORT).show()
                    }else{
                        progressbarlogin.visibility=View.GONE
                        Toast.makeText(this@MainActivity5Login,"Успешно!", Toast.LENGTH_SHORT).show()
                        edittextlogpassword.setError("Неправильный пароль!")
                    }
                }

            }
        }
    }
    fun save(text: String?) {
        val editor = getSharedPreferences("Pr", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("pr", text)
        editor.commit()
    }

    fun load() {
        val sharedPreferences = getSharedPreferences("Pr", MODE_PRIVATE)
        val savedtext = sharedPreferences.getString("pr", null)
        if (savedtext!=null){
            val intent=Intent(this@MainActivity5Login,MainActivity::class.java)
            intent.putExtra("uid",savedtext)
            startActivity(intent)
        }

    }
}