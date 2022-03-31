package ie.wit.work_iot_mobile.ui.settingsEdit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.work_iot_mobile.databinding.SettingsEditFragmentBinding
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.ui.settings.SettingsViewModel
import timber.log.Timber

class SettingsEditFragment : Fragment() {

    private lateinit var detailViewModel: SettingsEditViewModel
    private val args by navArgs<SettingsEditFragmentArgs>()
    private var _fragBinding: SettingsEditFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val settingsViewModel : SettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = SettingsEditFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(SettingsEditViewModel::class.java)
        detailViewModel.observableSettings.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editSettingsButton.setOnClickListener {
            detailViewModel.updateSettings(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.settingsId, fragBinding.settingsvm?.observableSettings!!.value!!)
            findNavController().navigateUp()
        }

        return root
    }


    private fun render() {
        fragBinding.settingsvm = detailViewModel
        Timber.i("settingsvm $fragBinding.settingsvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getSettings(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.settingsId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}