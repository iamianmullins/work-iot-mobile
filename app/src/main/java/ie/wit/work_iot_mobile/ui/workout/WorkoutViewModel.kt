package ie.wit.work_iot_mobile.ui.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel

class WorkoutViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addWorkout(workout: WorkoutModel) {
        status.value = try {
            WorkoutManager.create(workout)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}