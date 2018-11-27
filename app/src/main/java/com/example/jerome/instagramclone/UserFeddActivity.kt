package com.example.jerome.instagramclone

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class UserFeddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_fedd)

        var li = findViewById<LinearLayout>(R.id.line)
        var intent = getIntent()
        var username = intent.getStringExtra("username")
        setTitle(username + "'s Feed")
        var query : ParseQuery<ParseObject> = ParseQuery.getQuery("Image")
        query.whereEqualTo("username",username)
        query.findInBackground { objects, e ->
            if (e == null && objects.size > 0){
                Log.i("insinsins","find no error")
                for ( obj in objects){
                    var file = obj.get("image") as ParseFile
                    file.getDataInBackground{data,e ->
                        if (e == null && data != null){
                            var bitmap = BitmapFactory.decodeByteArray(data,0,data.size)
                            var imView = ImageView(getApplicationContext()) as ImageView
                            imView.setImageBitmap(bitmap)
                            li.addView(imView)
                        }else{
                            Log.i("insinsins","get online file error: " + e.message)
                        }
                    }
                }
            }else{
                Log.i("insinsins","find the user error: " + e.message)
            }
        }
    }
}
