package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    var _id: String = "N/A",
    @SerializedName("paymenttype")
    var exerciseType: String = "N/A",
    var repsSet1: String = "N/A",
    var reasonSet1: String = "N/A",
    var message: String = "n/a",
    var totalReps: Int = 0,
    val email: String = "joe@bloggs.com") : Parcelable