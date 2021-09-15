package com.example.gearchange

import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class GearChangListener : CarPropertyManager.CarPropertyEventCallback {

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onChangeEvent(value: CarPropertyValue<Any>) {
//        var currentGearChange = VEHICLE_GEARS.getOrDefault(value.value as Int, GEAR_UNKNOWN)
        var currentGearChange = VEHICLE_GEARS[value.value]?:GEAR_UNKNOWN

        Log.d("kajal", "onChangeEvent: " + currentGearChange)
    }

    override fun onErrorEvent(propId: Int, zone: Int) {
        Log.w("kajal", "Received error car property event, propId=$propId")
    }
}