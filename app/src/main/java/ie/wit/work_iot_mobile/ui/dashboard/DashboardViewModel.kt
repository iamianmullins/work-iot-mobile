package ie.wit.work_iot_mobile.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class DashboardViewModel : ViewModel() {
    var readOnly = MutableLiveData(false)

    private val workoutList =
        MutableLiveData<List<WorkoutModel>>()

    val observableWorkoutList: LiveData<List<WorkoutModel>>
        get() = workoutList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(workoutList)
            Timber.i("Dashboard LoadAll Success : ${workoutList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Dashboard LoadAll Error : $e.message")
        }
    }

    fun load() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, workoutList)
            Timber.i("Dashboard Load Success : ${workoutList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Dashboard Load Error : $e.message")
        }
    }
}



