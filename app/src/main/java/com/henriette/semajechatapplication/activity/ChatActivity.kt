package com.henriette.semajechatapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.henriette.semajechatapplication.R
import com.henriette.semajechatapplication.databinding.ActivityChatBinding
import com.henriette.semajechatapplication.model.User

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.tvUserName.text = user!!.userName

                if (user!!.profileImage == ""){
                    binding.ivProfile.setImageResource(R.drawable.semajebackground2)
                } else{
                    Glide.with(this@ChatActivity).load(user.profileImage).into(binding.ivProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        binding.btnSend.setOnClickListener{
            var message:String = binding.etMessage.toString()
            if (message.isEmpty()){
                Toast.makeText(applicationContext, "Empty!!", Toast.LENGTH_SHORT).show()

            }else{
                sendMessage(firebaseUser!!.uid,userId,message)
            }
        }

    }
    private fun sendMessage(senderId:String, receiverId:String, message: String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap:HashMap<String, String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)
    }
}