package com.example.jerome.instagramclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        var userlist : ListView = findViewById(R.id.userListView) as ListView
        var usernames : ArrayList<String> = ArrayList<String>()

        var query : ParseQuery<ParseObject>  = ParseQuery.getQuery("User")
        //query.whereNotEqualTo("username",ParseUser.getCurrentUser().username)
        Log.i("tshoot","before find")
        query.findInBackground { objects, e ->
            if ( e == null){
                for ( user in objects){
                    Log.i("tshoot","result" + objects.size)
                }
            }else{
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
        }



        var adapter  = ArrayAdapter(this,android.R.layout.simple_list_item_1,usernames)
        userlist.setAdapter(adapter)
    }
}
