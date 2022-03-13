package ie.wit.work_iot_mobile.main

import android.app.Application
import ie.wit.work_iot_mobile.models.WorkoutStore
import timber.log.Timber

class WorkIOTApp : Application() {

    //lateinit var workoutsStore: WorkoutStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
       // workoutsStore = WorkiotMemStore()
        Timber.i("WorkIOT Application Started")
    }
}