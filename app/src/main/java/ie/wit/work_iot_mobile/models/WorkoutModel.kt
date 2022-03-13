package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    var id: Long = 0,
    val exerciseType: String = "N/A",
    val repsSet1: String = "N/A",
    val reasonSet1: String = "N/A",
    val totalReps: Int = 0) : Parcelable