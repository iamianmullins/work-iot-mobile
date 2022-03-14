package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    var _id: String = "N/A",
    @SerializedName("paymenttype")
    val exerciseType: String = "N/A",
    val repsSet1: String = "N/A",
    val reasonSet1: String = "N/A",
    val message: String = "n/a",
    val totalReps: Int = 0) : Parcelable