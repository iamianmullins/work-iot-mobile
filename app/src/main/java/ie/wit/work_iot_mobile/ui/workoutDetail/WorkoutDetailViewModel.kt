package ie.wit.work_iot_mobile.ui.workoutDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel

class WorkoutDetailViewModel : ViewModel() {
    private val workout = MutableLiveData<WorkoutModel>()

    val observableWorkout: LiveData<WorkoutModel>
        get() = workout

    fun getWorkout(id: String) {
        workout.value = WorkoutManager.findById(id)
    }
}