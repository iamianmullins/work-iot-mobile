package ie.wit.work_iot_mobile.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class ReportViewModel : ViewModel() {
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
            Timber.i("Report LoadAll Success : ${workoutList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, workoutList)
            Timber.i("Report Load Success : ${workoutList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}

