package ie.wit.work_iot_mobile.main

import android.app.Application
import ie.wit.work_iot_mobile.models.WorkiotMemStore
import ie.wit.work_iot_mobile.models.WorkiotStore
import timber.log.Timber

class WorkIOTApp : Application() {

    lateinit var workoutsStore: WorkiotStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        workoutsStore = WorkiotMemStore()
        Timber.i("WorkIOT Application Started")
    }
}