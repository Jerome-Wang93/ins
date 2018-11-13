package com.example.jerome.instagramclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.SaveCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var query = ParseQuery.getQuery<ParseObject>("Score")
        query.whereEqualTo("username","jian")
        query.findInBackground(FindCallback { objects, e ->
            if ( objects == null ){
                Log.i("score","Failed")
            }else{
                Log.i("score",objects[0].getString("username"))
            }
        })
    }
}
