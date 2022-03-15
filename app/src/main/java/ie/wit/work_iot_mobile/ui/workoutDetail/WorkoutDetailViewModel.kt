package ie.wit.work_iot_mobile.ui.workoutDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.models.WorkoutManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class WorkoutDetailViewModel : ViewModel() {
    private val workout = MutableLiveData<WorkoutModel>()

    var observableWorkout: LiveData<WorkoutModel>
        get() = workout
        set(value) {workout.value = value.value}

    fun getWorkout(email:String, id: String) {
        try {
            WorkoutManager.findById(email, id, workout)
            Timber.i("Detail getWorkout() Success : ${workout.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getWorkout() Error : $e.message")
        }
    }

    fun updateWorkout(email:String, id: String, workout: WorkoutModel) {
        try {
            WorkoutManager.update(email, id, workout)
            Timber.i("Detail update() Success : $workout")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}