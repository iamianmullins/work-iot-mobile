package ie.wit.work_iot_mobile.models

import androidx.lifecycle.MutableLiveData
import ie.wit.work_iot_mobile.api.WorkoutClient
import ie.wit.work_iot_mobile.api.WorkoutWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object WorkoutManager : WorkoutStore {

    private val workouts = ArrayList<WorkoutModel>()

    override fun findAll(workoutsList: MutableLiveData<List<WorkoutModel>>) {
        val call = WorkoutClient.getApi().getall()
        call.enqueue(object : Callback<List<WorkoutModel>> {
            override fun onResponse(call: Call<List<WorkoutModel>>,
                                    response: Response<List<WorkoutModel>>
            ) {
                workoutsList.value = response.body() as ArrayList<WorkoutModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WorkoutModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:String) : WorkoutModel? {
        val foundWorkout: WorkoutModel? = workouts.find { it._id == id }
        return foundWorkout
    }

    override fun create(workout: WorkoutModel) {

        val call = WorkoutClient.getApi().post(workout)

        call.enqueue(object : Callback<WorkoutWrapper> {
            override fun onResponse(call: Call<WorkoutWrapper>,
                                    response: Response<WorkoutWrapper>
            ) {
                val workoutWrapper = response.body()
                if (workoutWrapper != null) {
                    Timber.i("Retrofit ${workoutWrapper.message}")
                    Timber.i("Retrofit ${workoutWrapper.data.toString()}")
                }
            }
            override fun onFailure(call: Call<WorkoutWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(id: String) {
        val call = WorkoutClient.getApi().delete(id)
        call.enqueue(object : Callback<WorkoutWrapper> {
            override fun onResponse(call: Call<WorkoutWrapper>,
                                    response: Response<WorkoutWrapper>
            ) {
                val workoutWrapper = response.body()
                if (workoutWrapper != null) {
                    Timber.i("Retrofit Delete ${workoutWrapper.message}")
                    Timber.i("Retrofit Delete ${workoutWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<WorkoutWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Workouts List **")
        workouts.forEach { Timber.v("Workout ${it}") }
    }
}