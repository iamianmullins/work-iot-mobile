package ie.wit.work_iot_mobile.ui.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.WorkoutModel

class WorkoutViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addWorkout(firebaseUser: MutableLiveData<FirebaseUser>,
                   workout: WorkoutModel) {
        status.value = try {
            FirebaseDBManager.create(firebaseUser,workout)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}