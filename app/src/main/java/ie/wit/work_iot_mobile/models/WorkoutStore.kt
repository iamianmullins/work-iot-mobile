package ie.wit.work_iot_mobile.models

import androidx.lifecycle.MutableLiveData

interface WorkoutStore {
    fun findAll(workoutList:
                MutableLiveData<List<WorkoutModel>>)
    fun findAll(email: String, workoutList:
    MutableLiveData<List<WorkoutModel>>)
    fun findById(email:String, id: String,
                 workout: MutableLiveData<WorkoutModel>)
    fun create(workout: WorkoutModel)
    fun delete(email:String,id: String)
    fun update(email:String,id: String,workout: WorkoutModel)
}