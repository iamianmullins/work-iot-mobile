package ie.wit.work_iot_mobile.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class ReportViewModel : ViewModel() {

    private val workoutList =
        MutableLiveData<List<WorkoutModel>>()

    val observableWorkoutList: LiveData<List<WorkoutModel>>
        get() = workoutList

    init { load() }

    fun load() {
        try {
            WorkoutManager.findAll(workoutList)
            Timber.i("Retrofit Load Success : $workoutList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Load Error : $e.message")
        }
    }

    fun delete(id: String) {
        try {
            WorkoutManager.delete(id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}

