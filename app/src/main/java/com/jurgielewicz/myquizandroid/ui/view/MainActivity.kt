package com.jurgielewicz.myquizandroid.ui.view

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.FacebookSdk
import com.jurgielewicz.myquizandroid.R
import java.security.MessageDigest
import java.util.*

class MainActivity : AppCompatActivity() {
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FacebookSdk.sdkInitialize(this)
        manager.beginTransaction().add(R.id.fragmentHolder, LoginFragment())
                .commit()

//        try {
//            val info = this.packageManager.getPackageInfo(this.packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.getEncoder().encode(md.digest()))
//                Log.i("AppLog", "key:$hashKey=")
//            }
//        } catch (e: Exception) {
//            Log.e("AppLog", "error:", e)
//        }
    }
}
