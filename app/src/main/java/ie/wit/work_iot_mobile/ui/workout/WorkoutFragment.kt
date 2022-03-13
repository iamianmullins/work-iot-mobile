package ie.wit.work_iot_mobile.ui.workout

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.FragmentWorkoutBinding
import ie.wit.work_iot_mobile.models.WorkoutModel


class WorkoutFragment : Fragment() {
    var totalRepCount = 0
    private var _fragBinding: FragmentWorkoutBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_workout)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        val reasons = resources.getStringArray(R.array.reasons)
        val repPickerCurrentArray = IntArray(5)

        fragBinding.repPicker1.minValue = 0
        fragBinding.repPicker1.maxValue = 25
        fragBinding.reasonPicker1.minValue = 0
        fragBinding.reasonPicker1.maxValue = reasons.size-1
        fragBinding.reasonPicker1.displayedValues= reasons

        fragBinding.repPicker1.setOnValueChangedListener { _, _, newVal ->
            repPickerCurrentArray[0] = newVal
            //Display the newly selected number to repCounter
            var one: Int = repPickerCurrentArray.sum()
            fragBinding.repCounter.setText("$one")
        }




        setButtonListener(fragBinding)
        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.workoutError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentWorkoutBinding) {
        layout.createButton.setOnClickListener {
            val totalReps = if (layout.repCounter.text.isNotEmpty())
                layout.repCounter.text.toString()
                    .toInt() else layout.repPicker1.value
            val exerciseType =
                if (layout.exerciseType.checkedRadioButtonId == R.id.Benchpress)
                    "Bench-press"
                else if (layout.exerciseType.checkedRadioButtonId == R.id.Deadlift)
                    "Deadlifts"
                else "Squats"
            fragBinding.reasonPicker1.setOnValueChangedListener { _, _, newVal ->
            }
            val reasonSet1 =
                if (layout.reasonPicker1.value.toString() == "0")
                    "N/A"
                else if (layout.reasonPicker1.value.toString() == "1")
                    "Bar-tilt"
                else if (layout.reasonPicker1.value.toString() == "2")
                    "Fatigue"
                else "Other"

            val repsSet1 = layout.repPicker1.value.toString()
            totalRepCount += totalReps
            layout.progressBar.progress = totalRepCount
            workoutViewModel.addWorkout(WorkoutModel(
                exerciseType = exerciseType,
                totalReps = totalReps,
                repsSet1 = repsSet1,
                reasonSet1 = reasonSet1
            ))
            Toast.makeText(context, "$exerciseType workout successfully added", Toast.LENGTH_SHORT).show()
            totalRepCount = 0
            //Timber.i("Total amount of reps entered $totalReps")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_workout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        totalRepCount = 0
        fragBinding.progressBar.progress = totalRepCount
    }
}