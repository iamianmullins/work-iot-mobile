package ie.wit.work_iot_mobile.models

interface WorkiotStore {
    fun findAll() : List<WorkiotModel>
    fun findById(id: Long) : WorkiotModel?
    fun create(workout: WorkiotModel)
}