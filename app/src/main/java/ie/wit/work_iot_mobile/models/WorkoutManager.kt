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

    override fun findAll(workoutList: MutableLiveData<List<WorkoutModel>>) {

        val call = WorkoutClient.getApi().findall()
        call.enqueue(object : Callback<List<WorkoutModel>> {
            override fun onResponse(call: Call<List<WorkoutModel>>,
                                    response: Response<List<WorkoutModel>>
            ) {
                workoutList.value = response.body() as ArrayList<WorkoutModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WorkoutModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }


    override fun findAll(email: String, workoutList: MutableLiveData<List<WorkoutModel>>) {

        val call = WorkoutClient.getApi().findall(email)
        call.enqueue(object : Callback<List<WorkoutModel>> {
            override fun onResponse(call: Call<List<WorkoutModel>>,
                                    response: Response<List<WorkoutModel>>
            ) {
                workoutList.value = response.body() as ArrayList<WorkoutModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<WorkoutModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun create(workout: WorkoutModel) {

        val call = WorkoutClient.getApi().post(workout.email,workout)
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

    override fun findById(email: String, id: String, workout: MutableLiveData<WorkoutModel>)   {

        val call = WorkoutClient.getApi().get(email,id)
        call.enqueue(object : Callback<WorkoutModel> {
            override fun onResponse(call: Call<WorkoutModel>, response: Response<WorkoutModel>) {
                workout.value = response.body() as WorkoutModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<WorkoutModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun delete(email: String,id: String) {

        val call = WorkoutClient.getApi().delete(email,id)
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

    override fun update(email: String,id: String, workout: WorkoutModel) {

        val call = WorkoutClient.getApi().put(email,id,workout)
        call.enqueue(object : Callback<WorkoutWrapper> {
            override fun onResponse(call: Call<WorkoutWrapper>,
                                    response: Response<WorkoutWrapper>
            ) {
                val workoutWrapper = response.body()
                if (workoutWrapper != null) {
                    Timber.i("Retrofit Update ${workoutWrapper.message}")
                    Timber.i("Retrofit Update ${workoutWrapper.data.toString()}")
                }
            }
            override fun onFailure(call: Call<WorkoutWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Workouts List **")
        workouts.forEach { Timber.v("Workout ${it}") }
    }
}