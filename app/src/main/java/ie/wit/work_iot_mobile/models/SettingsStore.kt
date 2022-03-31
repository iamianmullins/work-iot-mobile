package ie.wit.work_iot_mobile.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface SettingsStore {
    fun findAll(
        userid: String,
        settingsList:
        MutableLiveData<List<SettingsModel>>
    )

    fun findById(
        userid: String, settingsId: String,
        workout: MutableLiveData<SettingsModel>
    )

    fun create(firebaseUser: MutableLiveData<FirebaseUser>, settings: SettingsModel)
    fun delete(userid: String, settingsId: String)
    fun update(userid: String, settingsId: String, settings: SettingsModel)
}