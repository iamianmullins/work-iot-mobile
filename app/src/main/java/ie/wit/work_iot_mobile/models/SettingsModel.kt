package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class SettingsModel(
    var uid: String = "N/A",
    var exerciseGoal: String = "N/A",
    var nfc: String = "N/A",
    var oneRmBp: String = "0",
    var oneRmDl: String = "0",
    var oneRmSq: String = "0",
    var guuid: String = "0",
    val email: String = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "exerciseGoal" to exerciseGoal,
            "nfc" to nfc,
            "oneRmBp" to oneRmBp,
            "oneRmDl" to oneRmDl,
            "oneRmSq" to oneRmSq,
            "email" to email
        )
    }
}