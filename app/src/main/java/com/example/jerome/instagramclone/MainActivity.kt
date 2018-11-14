package com.example.jerome.instagramclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.parse.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var signUpstatus : Boolean = true
    override fun onClick(v: View) {

        if (v.getId() == R.id.changemode){
            var signupButton : Button = findViewById(R.id.signup) as Button
            var c : TextView = findViewById(R.id.changemode) as TextView
            if (signUpstatus){
                signUpstatus = false
                signupButton.setText("Log in")
                c.setText("or Sign up")
            }else{
                signUpstatus = true
                signupButton.setText("Sign up")
                c.setText("or Log in")
            }
        }
    }

    fun signUp (view : View){
        var username : EditText = findViewById(R.id.username) as EditText
        var password : EditText = findViewById(R.id.password) as EditText
        if(username.getText().toString() == "" || password.getText().toString() == ""){
            Toast.makeText(this,"A username and password is required",Toast.LENGTH_SHORT).show()
        }else{
            if ( signUpstatus){
                var user : ParseUser = ParseUser()
                user.setUsername(username.getText().toString())
                user.setPassword(password.getText().toString())
                user.signUpInBackground(SignUpCallback {
                    if ( it == null){
                        Toast.makeText(this,"Sign up successful",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    }
                })
            }else {
                ParseUser.logInInBackground(username.getText().toString(),password.getText().toString(),
                        LogInCallback { user, e ->
                            if ( e == null ){
                                Toast.makeText(this,"Log in successful",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                            }
                        })
            }

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var changeText : TextView = findViewById(R.id.changemode) as TextView
        changeText.setOnClickListener(this)

    }


}
