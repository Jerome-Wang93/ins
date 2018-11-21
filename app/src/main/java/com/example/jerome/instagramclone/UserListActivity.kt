package com.example.jerome.instagramclone

import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.parse.FindCallback
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.jar.Manifest

class UserListActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.share_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == R.id.share ){
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(android.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    }
            }else{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        var userlist = findViewById<View>(R.id.userListView) as ListView
        var arrayList  = ArrayList<String>()

        var query : ParseQuery<ParseUser> = ParseUser.getQuery()
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().username)
        query.findInBackground { objects, e ->
            if (e == null && objects.size > 0){
                for ( user in objects){
                    arrayList.add(user.getString("username").toString())
                }
            }else{
                Log.i("ttt","no")
            }
        }

        var adapter : ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList)
        userlist.setAdapter(adapter)

    }
}
