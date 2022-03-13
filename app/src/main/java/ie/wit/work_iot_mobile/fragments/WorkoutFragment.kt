package ie.wit.work_iot_mobile.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.FragmentWorkoutBinding
import ie.wit.work_iot_mobile.main.WorkIOTApp
import ie.wit.work_iot_mobile.models.WorkiotModel
import timber.log.Timber

class WorkoutFragment : Fragment() {
    lateinit var app: WorkIOTApp
    var totalRepCount = 0
    private var _fragBinding: FragmentWorkoutBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkIOTApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_workout)

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


    companion object {
        @JvmStatic
        fun newInstance() =
            WorkoutFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentWorkoutBinding) {
        layout.createButton.setOnClickListener {
            val totalReps = if (fragBinding.repCounter.text.isNotEmpty())
                fragBinding.repCounter.text.toString()
                    .toInt() else fragBinding.repPicker1.value
            val exerciseType =
                if (fragBinding.exerciseType.checkedRadioButtonId == R.id.Benchpress)
                    "Bench-press"
                else if (fragBinding.exerciseType.checkedRadioButtonId == R.id.Deadlift)
                    "Deadlifts"
                else "Squats"
            val repsSet1 = fragBinding.repPicker1.toString()
            val reasonSet1 = fragBinding.reasonPicker1.toString()

            totalRepCount += totalReps
            fragBinding.progressBar.progress = totalRepCount
            app.workoutsStore.create(
                WorkiotModel(
                    exerciseType = exerciseType,
                    totalReps = totalReps,
                    repsSet1 = repsSet1,
                    reasonSet1 = reasonSet1
                )
            )
            Timber.i("Total amount of reps entered $totalReps")
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
        totalRepCount = app.workoutsStore.findAll().sumOf { it.totalReps }
        fragBinding.progressBar.progress = totalRepCount
    }
}