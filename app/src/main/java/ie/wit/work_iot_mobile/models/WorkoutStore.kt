package ie.wit.work_iot_mobile.models

interface WorkoutStore {
    fun findAll() : List<WorkoutModel>
    fun findById(id: Long) : WorkoutModel?
    fun create(workout: WorkoutModel)
}