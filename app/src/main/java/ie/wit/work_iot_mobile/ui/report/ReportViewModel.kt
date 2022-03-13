package ie.wit.work_iot_mobile.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel

class ReportViewModel : ViewModel() {

    private val workoutList = MutableLiveData<List<WorkoutModel>>()

    val observableWorkoutList: LiveData<List<WorkoutModel>>
        get() = workoutList

    init {
        load()
    }

    fun load() {
        workoutList.value = WorkoutManager.findAll()
    }
}