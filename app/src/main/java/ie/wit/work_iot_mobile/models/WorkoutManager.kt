package ie.wit.work_iot_mobile.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object WorkoutManager : WorkoutStore {

    private val workouts = ArrayList<WorkoutModel>()

    override fun findAll(): List<WorkoutModel> {
        return workouts
    }

    override fun findById(id:Long) : WorkoutModel? {
        val foundWorkout: WorkoutModel? = workouts.find { it.id == id }
        return foundWorkout
    }

    override fun create(workout: WorkoutModel) {
        workout.id = getId()
        workouts.add(workout)
        logAll()
    }

    fun logAll() {
        Timber.v("** Workouts List **")
        workouts.forEach { Timber.v("Workout ${it}") }
    }
}