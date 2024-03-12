package com.henriette.semajechatapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.henriette.semajechatapplication.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    //setting up the viewbinding for this activity
    private lateinit var binding:ActivitySignUpBinding

    //setting up the action bar for the activity
//    private lateinit var actionBar: ActionBar

    //set up of a progress bar
//    private lateinit var progressDialog: ProgressDialog
//
    private lateinit var auth:FirebaseAuth
//    private lateinit var email: String
//    private lateinit var password: String

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuring the action bar
//        actionBar = supportActionBar!!
//        actionBar.title = " Sign Up "

        //Enabling the Back button
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)

        //configuring the progress dialog
//        progressDialog = ProgressDialog(this)
//        progressDialog.setTitle("Please Wait")
//        progressDialog.setMessage("Signing You up")
//        progressDialog.setCanceledOnTouchOutside(false)

        //Configuring Firebase auth
        auth = FirebaseAuth.getInstance()

        //Handling all clicks for the signup
        binding.btnSignUp.setOnClickListener {
//            validateSignUpData()
            val userName=binding.etEnterName.text.toString()
            val email = binding.etEnterEmail.text.toString()
            val password = binding.etEnterPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            
            if (TextUtils.isEmpty(userName)){
                Toast.makeText( applicationContext,"The username is required", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(email)){
                Toast.makeText( applicationContext,"The email is required", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText( applicationContext,"The password is required", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(confirmPassword)){
                Toast.makeText( applicationContext,"The confirm password is required", Toast.LENGTH_SHORT).show()
            }
            if(password != confirmPassword){
                Toast.makeText(applicationContext, "Password does not match", Toast.LENGTH_SHORT)
                    .show()
            }
            if (!isPasswordValid(password)) {
                binding.etEnterPassword.error =
                    "Password must contain at least one lowercase letter, one uppercase letter, and one special character"
            }



            registerUser(userName, email, password)

        }



//        binding.btnSignUp.setOnClickListener {
//            if (binding.etEnterName.text!!.isEmpty()){
//                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
//            }
//            else if(binding.etEnterEmail.text!!.isEmpty()){
//                Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
//            }
//            else if (binding.etEnterPassword.text!!.isEmpty()){
//                Toast.makeText(this, "Password must be more than 8 characters", Toast.LENGTH_SHORT).show()
//            }
//            else if (!binding.etConfirmPassword.equals(binding.etEnterPassword) || binding.etConfirmPassword.text!!.isEmpty()){
//                Toast.makeText(this, "Password doesn't Match", Toast.LENGTH_SHORT).show()
//            }
//
//        }


    }

//    private fun validateSignUpData() {
//        email = binding.etEnterEmail.text.toString().trim()
//        password = binding.etEnterPassword.text.toString().trim()
//
//        //validation of data
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            binding.etEnterEmail.error = "Invalid Email Format"
//
//        } else if (TextUtils.isEmpty(password)) {
//            binding.etEnterPassword.error = "Please enter a password"
//        } else if (password.length < 8) {
//            binding.etEnterPassword.error = "Password is too less than 8 characters"
//        } else if (!isPasswordValid(password)) {
//                binding.etEnterPassword.error =
//                    "Password must contain at least one lowercase letter, one uppercase letter, and one special character"
//            }
//        else{
//            firebaseSignUp()
//        }
//
//
//    }
//
//    private fun firebaseSignUp() {
//        progressDialog.show()
//
//        .createUserWithEmailAndPassword(email, password).addOnSuccessListener {
//            progressDialog.dismiss()
//            val  firebaseUser  = .currentUser
//            val email = firebaseUser!!.email
//            Toast.makeText(this, "SignUp successfull with email $email", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, ProfileActivity::class.java))
//            finish()
//        }
//            .addOnFailureListener { e->
//                progressDialog.dismiss()
//                Toast.makeText(this, "SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//
    private fun isPasswordValid(password: String): Boolean {
        val pattern =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
//
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed() //takes back to previous activity when the back is pressed
//        return super.onSupportNavigateUp()
//    }

    private fun registerUser( userName:String, email: String, password:String){
        auth.createUserWithEmailAndPassword(email,password,)
            .addOnCompleteListener(this){

                //validation of data
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    binding.etEnterEmail.error = "Invalid Email Format"
//
//                } else if (TextUtils.isEmpty(password)) {
//                    binding.etEnterPassword.error = "Please enter a password"
//                } else if (password.length < 8) {
//                    binding.etEnterPassword.error = "Password is too less than 8 characters"
//                } else if (!isPasswordValid(password)) {
//                    binding.etEnterPassword.error =
//                        "Password must contain at least one lowercase letter, one uppercase letter, and one special character"
//                }
//                else{
//                    firebaseSignUp()
//                }


                if (it.isSuccessful){
                    var user: FirebaseUser? = auth.currentUser
                    var userId:String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    var hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userId",userId)
                    hashMap.put("userName", userName)
                    hashMap.put("profileImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if (it.isSuccessful){
                            binding.etEnterName.setText("")
                            binding.etEnterEmail.setText("")
                            binding.etEnterPassword.setText("")
                            binding.etConfirmPassword.setText("")
                            var intent = Intent(this, UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

            }

    }
}