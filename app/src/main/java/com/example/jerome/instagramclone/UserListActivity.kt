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
import com.parse.FindCallback
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_user_list.*
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

            }else{
                sharePhoto()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            var image : Uri = data.data
            var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,image)
            var imageView : ImageView = findViewById(R.id.image) as ImageView
            imageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
    }
}
