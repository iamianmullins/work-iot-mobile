package ie.wit.work_iot_mobile.ui.workoutDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.work_iot_mobile.databinding.WorkoutDetailFragmentBinding
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.ui.report.ReportViewModel
import ie.wit.work_iot_mobile.utils.*
import timber.log.Timber


class WorkoutDetailFragment : Fragment() {
    var repPickerMax = 15
    var pickerMin = 0
    var maxPickerWeight = 250
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

        fragBinding.workoutReps1.minValue = pickerMin
        fragBinding.workoutReps1.maxValue = repPickerMax
        fragBinding.workoutReps2.minValue = pickerMin
        fragBinding.workoutReps2.maxValue = repPickerMax
        fragBinding.workoutReps3.minValue = pickerMin
        fragBinding.workoutReps3.maxValue = repPickerMax
        fragBinding.workoutReps4.minValue = pickerMin
        fragBinding.workoutReps4.maxValue = repPickerMax
        fragBinding.workoutReps5.minValue = pickerMin
        fragBinding.workoutReps5.maxValue = repPickerMax
        fragBinding.workingWeight.minValue = pickerMin
        fragBinding.workingWeight.maxValue = maxPickerWeight

        fragBinding.editWorkoututton.setOnClickListener {
            val totalreps = getTotal()
            fragBinding.workoutsvm?.observableWorkout!!.value!!.totalReps= totalreps;
            fragBinding.workoutsvm?.observableWorkout!!.value!!.timestamp = getTime()

            //Set Goal Position
            var goalPosition = fragBinding.workoutGoal.selectedItemPosition
            var goalString = getGoalStr(goalPosition)
            fragBinding.workoutsvm?.observableWorkout!!.value!!.exerciseGoal = goalString

            //Set workout Type Position
            var typePosition = fragBinding.workoutType.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.exerciseType = getTypeStr(typePosition)

            var reasonPosition1 = fragBinding.workoutReason1.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet1 = getReasonStr(reasonPosition1)

            var reasonPosition2 = fragBinding.workoutReason2.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet2 = getReasonStr(reasonPosition2)

            var reasonPosition3 = fragBinding.workoutReason3.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet3 = getReasonStr(reasonPosition3)

            var reasonPosition4 = fragBinding.workoutReason4.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet4 = getReasonStr(reasonPosition4)

            var reasonPosition5 = fragBinding.workoutReason5.selectedItemPosition
            fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet5 = getReasonStr(reasonPosition5)

            detailViewModel.updateWorkout(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.workoutid, fragBinding.workoutsvm?.observableWorkout!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteWorkoutButton.setOnClickListener {
            reportViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                detailViewModel.observableWorkout.value?.uid!!)
            findNavController().navigateUp()
        }
        fragBinding.linkWorkoutButton.setOnClickListener {
            setupHyperlink()
        }

        return root
    }

    private fun getTotal(): Int {
        val total = fragBinding.workoutsvm?.observableWorkout!!.value!!.repsSet1 +
        fragBinding.workoutsvm?.observableWorkout!!.value!!.repsSet2 +
        fragBinding.workoutsvm?.observableWorkout!!.value!!.repsSet3 +
        fragBinding.workoutsvm?.observableWorkout!!.value!!.repsSet4 +
        fragBinding.workoutsvm?.observableWorkout!!.value!!.repsSet5
        return total
    }


    private fun render() {
        fragBinding.workoutsvm = detailViewModel

        //Get Workout goal spinner position
        val goal = fragBinding.workoutsvm?.observableWorkout!!.value!!.exerciseGoal
        var goalPos = getGoalPos(goal)
        fragBinding.workoutGoal.setSelection(goalPos)

        //Get Workout goal spinner position
        val type = fragBinding.workoutsvm?.observableWorkout!!.value!!.exerciseType
        var typePos = getTypePos(type)
        fragBinding.workoutType.setSelection(typePos)

        val reasonList : MutableList<String> = ArrayList()
        reasonList.add(fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet1)
        reasonList.add(fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet2)
        reasonList.add(fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet3)
        reasonList.add(fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet4)
        reasonList.add(fragBinding.workoutsvm?.observableWorkout!!.value!!.reasonSet5)
        var setList : MutableList<Int> = (getFailPos(reasonList))
        fragBinding.workoutType.setSelection(typePos)
        fragBinding.workoutReason1.setSelection(setList[0])
        fragBinding.workoutReason2.setSelection(setList[1])
        fragBinding.workoutReason3.setSelection(setList[2])
        fragBinding.workoutReason4.setSelection(setList[3])
        fragBinding.workoutReason5.setSelection(setList[4])

        Timber.i("fragBinding.workoutsvm == $fragBinding.workoutsvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getWorkout(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.workoutid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    fun setupHyperlink() {
        var typePosition = fragBinding.workoutType.selectedItemPosition
        var type = getTypeStr(typePosition)
        when (type) {
            "Bench-Press" -> {
                val link = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vthMCtgVtFw&t=137s"))
                startActivity(link)}
            "Deadlift" -> {
                val link = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=hCDzSR6bW10&t=47s"))
                startActivity(link)}
            else -> {
                val link = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=nEQQle9-0NA"))
                startActivity(link)}
        }

    }
}