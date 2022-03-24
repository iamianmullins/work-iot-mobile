package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class WorkoutModel(
    var uid: String = "N/A",
    var exerciseType: String = "N/A",
    var repsSet1: String = "N/A",
    var reasonSet1: String = "N/A",
    var repsSet2: String = "N/A",
    var reasonSet2: String = "N/A",
    var repsSet3: String = "N/A",
    var reasonSet3: String = "N/A",
    var repsSet4: String = "N/A",
    var reasonSet4: String = "N/A",
    var repsSet5: String = "N/A",
    var reasonSet5: String = "N/A",
    var message: String = "n/a",
    var totalReps: Int = 0,
    val email: String = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "exerciseType" to exerciseType,
            "repsSet1" to repsSet1,
            "reasonSet1" to reasonSet1,
            "repsSet2" to repsSet2,
            "reasonSet2" to reasonSet2,
            "repsSet3" to repsSet3,
            "reasonSet3" to reasonSet3,
            "repsSet4" to repsSet4,
            "reasonSet4" to reasonSet4,
            "repsSet5" to repsSet5,
            "reasonSet5" to reasonSet5,
            "message" to message,
            "totalReps" to totalReps,
            "email" to email
        )
    }
}