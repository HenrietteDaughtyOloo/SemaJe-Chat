package com.henriette.semajechatapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.henriette.semajechatapplication.R
import com.henriette.semajechatapplication.adapter.UserAdapter
import com.henriette.semajechatapplication.databinding.ActivityUsersBinding
import com.henriette.semajechatapplication.model.User

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding

    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUsersBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayout.VERTICAL, false)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivProfile.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        getUsersList()


//        userList.add(User("Jefferson", "https://homepage.cae.wise.edu/mece533/images/cat.png"))
//        userList.add(User("Jefferson", "https://homepage.cae.wise.edu/mece533/images/cat.png"))
//        userList.add(User("Jefferson", "https://homepage.cae.wise.edu/mece533/images/cat.png"))
//        userList.add(User("Jefferson", "https://homepage.cae.wise.edu/mece533/images/cat.png"))
//        userList.add(User("Jefferson", "https://homepage.cae.wise.edu/mece533/images/cat.png"))






    }

    fun getUsersList(){
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var  databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                val currentUser = snapshot.getValue(User::class.java)


                if (currentUser!!.userImage == ""){
                    binding.ivProfile.setImageResource(R.drawable.semajebackground2)
                }
                else{
                    Glide.with(this@UsersActivity).load(currentUser.userImage).into(binding.ivProfile)

                }

                for (dataSnapshot:DataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)){
                        userList.add(user)

                    }
                }
                val userAdapter = UserAdapter(this@UsersActivity,userList)
                binding.userRecyclerView.adapter =userAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message , Toast.LENGTH_SHORT).show()

            }

        })
    }
}