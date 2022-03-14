package ie.wit.work_iot_mobile.api

import ie.wit.work_iot_mobile.models.WorkoutModel
import retrofit2.Call
import retrofit2.http.*

interface WorkoutService {
    @GET("/donations")
    fun getall(): Call<List<WorkoutModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<WorkoutModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<WorkoutWrapper>

    @POST("/donations")
    fun post(@Body workout: WorkoutModel): Call<WorkoutWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body workout: WorkoutModel
    ): Call<WorkoutWrapper>
}