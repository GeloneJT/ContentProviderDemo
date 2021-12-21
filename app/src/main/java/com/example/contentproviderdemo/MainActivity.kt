package com.example.contentproviderdemo

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val contactFields = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.contact_list_view)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                Array(1){android.Manifest.permission.READ_CONTACTS},100
            )
        }
        else
            readUserContacts()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            readUserContacts()
        }
    }
    private fun readUserContacts(){
        val source = listOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()

        val destination = intArrayOf(android.R.id.text1, android.R.id.text2)
        val resultsSet = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        contactFields,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val adapter = SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, resultsSet,source,destination,0)

        listView.adapter = adapter
    }
}