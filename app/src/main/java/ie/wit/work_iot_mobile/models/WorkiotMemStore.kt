package ie.wit.work_iot_mobile.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class WorkiotMemStore : WorkiotStore {

    val workouts = ArrayList<WorkiotModel>()

    override fun findAll(): List<WorkiotModel> {
        return workouts
    }

    override fun findById(id:Long) : WorkiotModel? {
        val foundWorkout: WorkiotModel? = workouts.find { it.id == id }
        return foundWorkout
    }

    override fun create(workout: WorkiotModel) {
        workout.id = getId()
        workouts.add(workout)
        logAll()
    }

    fun logAll() {
        Timber.v("** Workout List **")
        workouts.forEach { Timber.v("Workout ${it}") }
    }
}