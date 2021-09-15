package com.example.gearchange

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

companion object {
        private const val GEAR_UNKNOWN = "GEAR_UNKNOWN"
        private val VEHICLE_GEARS = mapOf(
                0x0000 to GEAR_UNKNOWN,
                0x0001 to "GEAR_NEUTRAL",
                0x0002 to "GEAR_REVERSE",
                0x0004 to "GEAR_PARK",
                0x0008 to "GEAR_DRIVE"
        )
    }

    private lateinit var car: Car
    private lateinit var carPropertyManager: CarPropertyManager

    private var carPropertyListener = object : CarPropertyManager.CarPropertyEventCallback {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onChangeEvent(value: CarPropertyValue<Any>) {

            var currentGearChange =  VEHICLE_GEARS.getOrDefault(value.value as Int, GEAR_UNKNOWN)
            Log.d("kajal", "onChangeEvent: " + currentGearChange)
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.w("kajal", "Received error car property event, propId=$propId")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        car = Car.createCar(this)

        carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

        // Subscribes to the gear change events.
        carPropertyManager.registerCallback(
                carPropertyListener,
                VehiclePropertyIds.GEAR_SELECTION,
                CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        car.disconnect()
    }
}



//with UI

//    companion object {
//        private const val TAG = "kajal"
//        private const val GEAR_UNKNOWN = "GEAR_UNKNOWN"
//
//        private val VEHICLE_GEARS = mapOf(
//            0x0000 to GEAR_UNKNOWN,
//            0x0001 to "GEAR_NEUTRAL",
//            0x0002 to "GEAR_REVERSE",
//            0x0004 to "GEAR_PARK",
//            0x0008 to "GEAR_DRIVE"
//        )
//    }
//
//    private lateinit var currentGearTextView: TextView
//    private lateinit var car: Car
//    private lateinit var carPropertyManager: CarPropertyManager
//
//    private var carPropertyListener = object : CarPropertyManager.CarPropertyEventCallback {
//        @RequiresApi(Build.VERSION_CODES.N)
//        override fun onChangeEvent(value: CarPropertyValue<Any>) {
//            Log.d(TAG, "onChangeEvent: "+value.value.toString())
//            currentGearTextView.text = VEHICLE_GEARS.getOrDefault(value.value as Int, GEAR_UNKNOWN)
//            Log.d(TAG, "onChangeEvent: "+currentGearTextView.text)
//
//        }
//
//        override fun onErrorEvent(propId: Int, zone: Int) {
//            Log.w(TAG, "Received error car property event, propId=$propId")
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        currentGearTextView = findViewById(R.id.currentGearTextView)
//        car = Car.createCar(this)
//        carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
//
//        // Subscribes to the gear change events.
//        carPropertyManager.registerCallback(
//            carPropertyListener,
//            VehiclePropertyIds.GEAR_SELECTION,    // propertyId
//            CarPropertyManager.SENSOR_RATE_ONCHANGE // read on change value-0.0
//        )
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        car.disconnect()
//    }
//}

