package ie.wit.work_iot_mobile.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class SettingsModel(
    var uid: String = "N/A",
    var exerciseGoal: String = "Strength",
    var nfc: String = "",
    var oneRmBp: Int = 25,
    var oneRmDl: Int = 25,
    var oneRmSq: Int = 25,
    val email: String = "jimuser@workiot.com") : Parcelable
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