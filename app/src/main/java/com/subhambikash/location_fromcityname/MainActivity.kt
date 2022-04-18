package com.subhambikash.location_fromcityname

import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION))
        }else{
            getLocation.isEnabled=true
        }

        getLocation.setOnClickListener {
            val cityName=cityName.text.toString().trim()
            if (TextUtils.isEmpty(cityName)){
                Toast.makeText(this,"enter city name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val gc=Geocoder(this,Locale.getDefault())
                val addressList=gc.getFromLocationName(cityName,2)
                val address=addressList[0]
                location.text="latitude-${address.latitude}\nlongitude-${address.longitude}\nstate name-${address.adminArea}"
                location.visibility=View.VISIBLE
            }
        }




    }




    private val resultLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        it.entries.forEach { it1 ->
            if (it1.value){
                getLocation.isEnabled=true
            }
        }

    }
}