package com.henriette.semajechatapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.henriette.semajechatapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var actionBar: ActionBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        auth = FirebaseAuth.getInstance()

        checkUser()

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            checkUser()
        }



    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser!=null){
            val email = firebaseUser.email
            binding.tvLoggedInEmail.text = email


        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}