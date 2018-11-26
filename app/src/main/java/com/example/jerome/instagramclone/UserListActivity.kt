package com.example.jerome.instagramclone

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.parse.*
import kotlinx.android.synthetic.main.activity_user_list.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.jar.Manifest

class UserListActivity : AppCompatActivity() {

    var per = "Manifest.permission.READ_EXTERNAL_STORAGE"

    fun sharePhoto(){
        var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent,1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.share_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == R.id.share){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                sharePhoto()
            }else{
                sharePhoto()
            }
        }
        if ( item.itemId == R.id.logout){
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ( grantResults.size > 0 && requestCode == 1){
            sharePhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            var image : Uri = data.data
            try {
                var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,image)
                var stream : ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG,50,stream)
                var byteArray = stream.toByteArray()
                var file : ParseFile = ParseFile("image.png",byteArray)
                var imgview = findViewById<ImageView>(R.id.image)
                imgview.setImageBitmap(bitmap)
                file.saveInBackground { callback : ParseException? ->
                    if (callback == null){
                        Log.i("save","success")
                        var obj = ParseObject("Image")
                        obj.put("image",file)
                        obj.put("username",ParseUser.getCurrentUser().username)
                        obj.saveInBackground {
                            if (it == null){
                                Log.i("save","success")
                            }else{
                                Log.i("save",it.message)
                            }
                        }
                    }else{
                        Log.i("save",callback.message)
                    }
                }
            }catch (e : IOException){
                Log.i("try",e.message)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        var list = findViewById(R.id.listview) as ListView
        var usernames : ArrayList<String> =  ArrayList<String>()

        var query = ParseUser.getQuery()
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().username)
        query.findInBackground { objects, e ->
            if ( e == null && objects != null){
                for (user in objects){
                    usernames.add(user.username)
                }
            }else{
                Log.i("listview",e.message)
            }
        }
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,usernames)
        list.setAdapter(adapter)
    }
}
