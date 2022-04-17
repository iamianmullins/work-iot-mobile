package ie.wit.work_iot_mobile.ui.settingsEdit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.work_iot_mobile.databinding.SettingsEditFragmentBinding
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.ui.settings.SettingsViewModel
import ie.wit.work_iot_mobile.utils.getGoalPos
import ie.wit.work_iot_mobile.utils.getGoalStr
import timber.log.Timber

class SettingsEditFragment : Fragment() {

    private lateinit var detailViewModel: SettingsEditViewModel
    private val args by navArgs<SettingsEditFragmentArgs>()
    private var _fragBinding: SettingsEditFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val settingsViewModel : SettingsViewModel by activityViewModels()
    val goals = listOf("hypertrophy","endurance","strength")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = SettingsEditFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.settingsOnermBp.minValue = 1
        fragBinding.settingsOnermBp.maxValue = 350
        fragBinding.settingsOnermDl.minValue = 1
        fragBinding.settingsOnermDl.maxValue = 350
        fragBinding.settingsOnermSq.minValue = 1
        fragBinding.settingsOnermSq.maxValue = 350

        detailViewModel = ViewModelProvider(this).get(SettingsEditViewModel::class.java)
        detailViewModel.observableSettings.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editSettingsButton.setOnClickListener {
            var goal = fragBinding.settingsvm?.observableSettings!!.value!!.exerciseGoal!!.toLowerCase()
            if (goal !in goals) {
                Toast.makeText(context, "Please enter a valid goal! (Hypertrophy, Endurance, Strength", Toast.LENGTH_LONG).show()
            } else {

                //Set Workout goal
                var goalPosition = fragBinding.settingsGoal.selectedItemPosition
                var goalString = getGoalStr(goalPosition)
                fragBinding.settingsvm?.observableSettings!!.value!!.exerciseGoal = goalString


                detailViewModel.updateSettings(
                    loggedInViewModel.liveFirebaseUser.value?.uid!!,
                    args.settingsId, fragBinding.settingsvm?.observableSettings!!.value!!
                )
                findNavController().navigateUp()
            }
        }

        return root
    }


    private fun render() {
        fragBinding.settingsvm = detailViewModel

        //Get Workout goal spinner Position
        val goal = fragBinding.settingsvm?.observableSettings!!.value!!.exerciseGoal
        var goalPos = getGoalPos(goal)
        fragBinding.settingsGoal.setSelection(goalPos)
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