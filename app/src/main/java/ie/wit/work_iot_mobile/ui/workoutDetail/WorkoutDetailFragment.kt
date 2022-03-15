package ie.wit.work_iot_mobile.ui.workoutDetail

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
import ie.wit.work_iot_mobile.databinding.WorkoutDetailFragmentBinding
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.ui.report.ReportViewModel
import timber.log.Timber

class WorkoutDetailFragment : Fragment() {

    private lateinit var detailViewModel: WorkoutDetailViewModel
    private val args by navArgs<WorkoutDetailFragmentArgs>()
    private var _fragBinding: WorkoutDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val reportViewModel : ReportViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = WorkoutDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(WorkoutDetailViewModel::class.java)
        detailViewModel.observableWorkout.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editWorkoututton.setOnClickListener {
            detailViewModel.updateWorkout(loggedInViewModel.liveFirebaseUser.value?.email!!,
                args.workoutid, fragBinding.workoutsvm?.observableWorkout!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteWorkoutButton.setOnClickListener {
            reportViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.email!!,
                detailViewModel.observableWorkout.value?._id!!)
            findNavController().navigateUp()
        }

        return root
    }


    private fun render() {
        fragBinding.workoutsvm = detailViewModel
        Timber.i("Retrofit fragBinding.workoutsvm == $fragBinding.workoutsvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getWorkout(loggedInViewModel.liveFirebaseUser.value?.email!!,
            args.workoutid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}