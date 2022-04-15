package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class WorkoutModel(
    var uid: String = "N/A",
    var profilepic: String = "",
    var exerciseGoal: String = "N/A",
    var exerciseType: String = "N/A",
    var workingWeight: Int = 0,
    var repsSet1: Int = 0,
    var reasonSet1: String = "N/A",
    var repsSet2: Int = 0,
    var reasonSet2: String = "N/A",
    var repsSet3: Int = 0,
    var reasonSet3: String = "N/A",
    var repsSet4: Int = 0,
    var reasonSet4: String = "N/A",
    var repsSet5: Int = 0,
    var reasonSet5: String = "N/A",
    var totalReps: String = "0",
    var timestamp: String = "N/A",
    val email: String = "jim@user.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "profilepic" to profilepic,
            "exerciseGoal" to exerciseGoal,
            "workingWeight" to workingWeight,
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
            "totalReps" to totalReps,
            "timestamp" to timestamp,
            "email" to email
        )
    }
}