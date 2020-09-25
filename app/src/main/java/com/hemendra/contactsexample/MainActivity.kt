package com.hemendra.contactsexample

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var status = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)

        if(status == PackageManager.PERMISSION_GRANTED)
        {
            readcalllogs()
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG),123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
    {
        readcalllogs()
    }
        else{
        finish()
    }
    }

     fun readcalllogs()
     {
         var resolver:ContentResolver = contentResolver
         var curi = CallLog.Calls.CONTENT_URI
         var c:Cursor? = resolver.query(curi,null,null,null,null)

         var from_array = arrayOf(
             CallLog.Calls.CACHED_NAME,
             CallLog.Calls.NUMBER,
             CallLog.Calls.TYPE
         )

         var view_array =
             intArrayOf(R.id.tv_name,R.id.tv_phone,R.id.tv_type)

         var cadapter = SimpleCursorAdapter(this,R.layout.list_item,c,from_array,view_array,0)

         contacts_lv.adapter = cadapter

     }

    fun fetchContacts()
    {

           var resolver:ContentResolver = contentResolver

        var curi = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        var c:Cursor? = resolver.query(curi,null,null,null,null)

        var from_array = arrayOf(
              ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
              ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        var view_array =
            intArrayOf(R.id.tv_name,R.id.tv_phone)

        var cadapter = SimpleCursorAdapter(this,R.layout.list_item,c,from_array,view_array,0)

     contacts_lv.adapter = cadapter
    }
}