package ie.wit.work_iot_mobile.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class ReportViewModel : ViewModel() {

    private val workoutList =
        MutableLiveData<List<WorkoutModel>>()

    val observableWorkoutList: LiveData<List<WorkoutModel>>
        get() = workoutList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            WorkoutManager.findAll(liveFirebaseUser.value?.email!!, workoutList)
            Timber.i("Report Load Success : ${workoutList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            WorkoutManager.delete(email,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}

