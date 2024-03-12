package com.henriette.semajechatapplication.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.henriette.semajechatapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    //Enable View Binding for the activity
    private lateinit var binding: ActivityLoginBinding

    //set up an action bar
//    private lateinit var actionBar: ActionBar

    //setting up the Progress Dialog
    private lateinit var progressDialog: ProgressDialog

//    setting up the firebase Auth
    private lateinit var auth:FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configuring the action Bar
//        actionBar = supportActionBar!!
//        actionBar.title ="LogIn"

        //Configuring the progess dialog
//        progressDialog = ProgressDialog(this)
//        progressDialog.setTitle("Please Wait a few seconds")
//        progressDialog.setMessage("Logging In ....")
//        progressDialog.setCanceledOnTouchOutside(false)


        //Innitializing the firebase Auth
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        if (firebaseUser != null){
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Handling don't have an account?
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.btnLogIn.setOnClickListener {
            val email = binding.etEnterloggedInEmail.text.toString()
            val password = binding.etEnterPassword.text.toString()

            if (TextUtils.isEmpty(email)&&TextUtils.isEmpty(password)){
                Toast.makeText(
                    applicationContext,
                    "Email and password are required",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            binding.etEnterloggedInEmail.setText("")
                            binding.etEnterPassword.setText("")

                            //Handling the button click
                            startActivity(Intent(this, UsersActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(
                                applicationContext,
                                "Invalid email or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
        }
}