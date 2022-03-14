package ie.wit.work_iot_mobile.models

import androidx.lifecycle.MutableLiveData

interface WorkoutStore {
    fun findAll(workoutList: MutableLiveData<List<WorkoutModel>>)
    fun findById(id: String) : WorkoutModel?
    fun create(workout: WorkoutModel)
    fun delete(id: String)
}