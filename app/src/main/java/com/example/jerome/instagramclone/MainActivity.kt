package com.example.jerome.instagramclone

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.parse.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var signUpstatus : Boolean = true

    fun hideKeyboard(){
        var input : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0)
    }

    fun showUserlist(){
        var intent : Intent = Intent(this,UserListActivity :: class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParseUser.logOut()



        var background : RelativeLayout = findViewById(R.id.back) as RelativeLayout
        var imageView : ImageView = findViewById(R.id.logo) as ImageView
        var username : EditText = findViewById(R.id.username) as EditText
        var password : EditText = findViewById(R.id.password) as EditText
        var button : Button = findViewById(R.id.signup) as Button
        var chagemode : TextView = findViewById(R.id.changemode) as TextView

        background.setOnClickListener { l : View ->
            hideKeyboard()
        }

        imageView.setOnClickListener { l : View ->
            hideKeyboard()
        }

        button.setOnClickListener { l : View ->
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
                            showUserlist()
                        }else{
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                            Log.i("sign up",it.message)
                        }
                    })
                }else {
                    ParseUser.logInInBackground(username.getText().toString(),password.getText().toString(),
                            LogInCallback { user, e ->
                                if ( e == null ){
                                    Toast.makeText(this,"Log in successful",Toast.LENGTH_SHORT).show()
                                    showUserlist()
                                }else{
                                    Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                                }
                            })
                }

            }
        }

        changemode.setOnClickListener { l : View ->
            if ( signUpstatus){
                signUpstatus = false
                button.setText("Log in")
                chagemode.setText("Sign up")
            }else{
                signUpstatus = true
                button.setText("Sign up")
                chagemode.setText("Log in")
            }
        }
    }
}


