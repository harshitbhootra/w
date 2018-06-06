package com.visiontutor.app

import android.app.Application
import com.androidnetworking.AndroidNetworking

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Crouton.cancelAllCroutons()
        AndroidNetworking.initialize(this)

    }

}

fun main(args: Array<String>) {
    print("Test")
}
