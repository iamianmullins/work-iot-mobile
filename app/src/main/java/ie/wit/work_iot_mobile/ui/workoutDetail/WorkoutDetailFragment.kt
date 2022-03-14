package ie.wit.work_iot_mobile.ui.workoutDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.FragmentWorkoutBinding
import ie.wit.work_iot_mobile.databinding.WorkoutDetailFragmentBinding
import timber.log.Timber

class WorkoutDetailFragment : Fragment() {

    private lateinit var detailViewModel: WorkoutDetailViewModel
    private val args by navArgs<WorkoutDetailFragmentArgs>()
    private var _fragBinding: WorkoutDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = WorkoutDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(WorkoutDetailViewModel::class.java)
        detailViewModel.observableWorkout.observe(viewLifecycleOwner, Observer { render() })
        return root
    }


    private fun render() {
        fragBinding.workoutsvm = detailViewModel
        Timber.i("Retrofit fragBinding.workoutsvm == $fragBinding.workoutsvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getWorkout(args.workoutid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}