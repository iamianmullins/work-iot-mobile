package ie.wit.work_iot_mobile.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface WorkoutStore {
    fun findAll(
        userid: String,
        workoutList:
        MutableLiveData<List<WorkoutModel>>
    )

    fun findAll(workoutList:
                MutableLiveData<List<WorkoutModel>>)

    fun findById(
        userid: String, workoutId: String,
        workout: MutableLiveData<WorkoutModel>
    )

    fun create(firebaseUser: MutableLiveData<FirebaseUser>, workout: WorkoutModel)
    fun delete(userid: String, workoutId: String)
    fun update(userid: String, workoutId: String, workout: WorkoutModel)
    fun findSettingsById(userid: String, workoutId: String, workout: MutableLiveData<SettingsModel>)
}