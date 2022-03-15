package ie.wit.work_iot_mobile.api

import ie.wit.work_iot_mobile.models.WorkoutModel
import retrofit2.Call
import retrofit2.http.*

interface WorkoutService {
    @GET("/donations")
    fun findall(): Call<List<WorkoutModel>>

    @GET("/donations/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<WorkoutModel>>

    @GET("/donations/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<WorkoutModel>

    @DELETE("/donations/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<WorkoutWrapper>

    @POST("/donations/{email}")
    fun post(@Path("email") email: String?,
             @Body workout: WorkoutModel)
            : Call<WorkoutWrapper>

    @PUT("/donations/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body workout: WorkoutModel
    ): Call<WorkoutWrapper>
}