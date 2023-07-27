package com.sancho.online_shopping_2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import android.os.UserManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sancho.online_shopping_2.R
import com.sancho.online_shopping_2.databinding.ActivityMainActivity6RegisterBinding
import com.sancho.online_shopping_2.model.UserModel
import com.sancho.online_shopping_2.utitlits.Constants.USER_INFORMATION

class MainActivity6Register : AppCompatActivity() {
    lateinit var binding: ActivityMainActivity6RegisterBinding
    lateinit var firebaseaAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainActivity6RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseaAuth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION)

        binding.apply {
            buttonaddcategories.setOnClickListener {
                if (checkedittext()){
                    firebaseaAuth.createUserWithEmailAndPassword(edittextreglogin.text.toString(),edittextregpassword.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            var uid=firebaseaAuth.currentUser!!.uid
                            val userModel=UserModel(
                                name = edittextregname.text.toString(),
                                surname = edittextregsurname.text.toString(),
                                phone = edittextregphone.text.toString(),
                                address = edittextregaddress.text.toString(),
                                email = edittextreglogin.text.toString(),
                                password = edittextregpassword.text.toString(),
                                uid = uid
                            )
                            databaseReference.child(uid).setValue(userModel)
                            Toast.makeText(this@MainActivity6Register,"Успешно!", Toast.LENGTH_SHORT).show()
                            edittextregname.text.clear()
                            edittextregsurname.text.clear()
                            edittextregphone.text.clear()
                            edittextregaddress.text.clear()
                            edittextreglogin.text.clear()
                            edittextregpassword.text.clear()
                        }
                    }
                }

            }

        }

    }
    fun checkedittext(): Boolean {
        var nice: Boolean? = false
        binding.apply {
            if (edittextregname.text.isEmpty()) {
                edittextregname.setError("Напишите свое имя!")
            } else if (edittextregsurname.text.isEmpty()) {
                edittextregsurname.setError("Напишите свою фамилию!")
            } else if (edittextregphone.text.length < 8) {
                edittextregphone.setError("Номер телефона должен быть не меньше 8 цифр!")
            } else if (edittextregaddress.text.isEmpty()){
                edittextregaddress.setError("Напишите свой Адрес!")
            } else if (edittextreglogin.text.isEmpty()) {
                edittextreglogin.setError("Напишите свой логин!")
            } else if (edittextregpassword.text.isEmpty()) {
                edittextregpassword.setError("напишите пароль логина!")
            } else if (edittextregpassword.text.length < 8) {
                edittextregpassword.setError("Пароль должен быть не меньше 8 символов!")
            } else {
                nice = true
            }
            return nice!!
        }
    }
}