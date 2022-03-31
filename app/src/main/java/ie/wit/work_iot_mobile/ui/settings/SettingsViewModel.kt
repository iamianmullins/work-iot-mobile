package ie.wit.work_iot_mobile.ui.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.work_iot_mobile.firebase.FirebaseDBManager
import ie.wit.work_iot_mobile.models.SettingsModel
import timber.log.Timber
import java.lang.Exception

class SettingsViewModel : ViewModel() {

    private val settingList =
        MutableLiveData<List<SettingsModel>>()

    val observableSettingsList: LiveData<List<SettingsModel>>
        get() = settingList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            FirebaseDBManager.findLatest(liveFirebaseUser.value?.uid!!, settingList)
            Timber.i("Report Load Success : ${settingList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun addInitialSettings(firebaseUser: MutableLiveData<FirebaseUser>,
                   settings: SettingsModel) {
        try {
            FirebaseDBManager.createInitialSettings(firebaseUser,settings)
            Timber.i("add settings Delete Success")
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}

