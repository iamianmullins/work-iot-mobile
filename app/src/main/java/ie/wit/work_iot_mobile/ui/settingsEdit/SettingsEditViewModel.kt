package ie.wit.work_iot_mobile.ui.settingsEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.SettingsModel
import timber.log.Timber
import java.lang.Exception

class SettingsEditViewModel : ViewModel() {
    private val userSetting = MutableLiveData<SettingsModel>()

    var observableSettings: LiveData<SettingsModel>
        get() = userSetting
        set(value) {userSetting.value = value.value}

    fun getSettings(userid:String, id: String) {
        try {
            FirebaseDBManager.findSettingsById(userid, id, userSetting)
            Timber.i("Detail getSettings() Success : ${FirebaseDBManager.findSettingsById(userid, id, userSetting)}")
            Timber.i("Detail getSettings() Success : ${userSetting.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getSettings() Error : $e.message")
        }
    }

    fun updateSettings(userid:String, id: String,userSetting: SettingsModel) {
        try {
            FirebaseDBManager.updateSettings(userid, id, userSetting)
            Timber.i("Settings update() Success : $userSetting")
        }
        catch (e: Exception) {
            Timber.i("Settings update() Error : $e.message")
        }
    }
}