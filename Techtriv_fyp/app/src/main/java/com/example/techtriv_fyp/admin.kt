package com.example.techtriv_fyp

import UserAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.techtriv_fyp.Models.Users
import com.example.techtriv_fyp.databinding.ActivityAdminBinding
import com.example.techtriv_fyp.databinding.ActivitySignUpBinding
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class admin : AppCompatActivity() {
    val binding by lazy {
        ActivityAdminBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var userList = ArrayList<Users>()
        var adapter = UserAdapter(this, userList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection("Users").get().addOnSuccessListener() {
            var templist = arrayListOf<Users>()
            for (i in it.documents) {
                var user: Users= i.toObject<Users>()!!
                templist.add(user)
            }
            userList.addAll(templist)
        }
    }
}