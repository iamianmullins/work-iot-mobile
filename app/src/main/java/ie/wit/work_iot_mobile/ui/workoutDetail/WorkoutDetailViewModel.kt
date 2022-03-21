package ie.wit.work_iot_mobile.ui.workoutDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.WorkoutModel
import timber.log.Timber
import java.lang.Exception

class WorkoutDetailViewModel : ViewModel() {
    private val workout = MutableLiveData<WorkoutModel>()

    var observableWorkout: LiveData<WorkoutModel>
        get() = workout
        set(value) {workout.value = value.value}

    fun getWorkout(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, workout)
            Timber.i("Detail getWorkout() Success : ${workout.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getWorkout() Error : $e.message")
        }
    }

    fun updateWorkout(userid:String, id: String,workout: WorkoutModel) {
        try {
            FirebaseDBManager.update(userid, id, workout)
            Timber.i("Detail update() Success : $workout")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}