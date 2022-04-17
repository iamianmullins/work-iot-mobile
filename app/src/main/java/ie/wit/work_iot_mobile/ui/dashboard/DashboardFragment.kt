package ie.wit.work_iot_mobile.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.FragmentDashboardBinding
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.utils.*
import java.math.RoundingMode
import java.text.DecimalFormat

class DashboardFragment : Fragment() {

    private var _fragBinding: FragmentDashboardBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val dashbordViewModel: DashboardViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        showLoader(loader,"Downloading Workouts")
        dashbordViewModel.observableWorkoutList.observe(viewLifecycleOwner, Observer {

                workouts ->
            workouts?.let {
                hideLoader(loader)
                checkSwipeRefresh()
            }

            var totalWorkouts = workouts.size
            var benchCounter = 0; var deadliftCounter = 0; var squatCounter = 0; var totalReps = 0
            var reason1Count = 0; var reason2Count = 0; var reason3Count = 0
            var reason4Count = 0; var reason5Count = 0
            val failList = mutableListOf<String>()
            var maxFailure = 0
            var maxFailureStr = ""
            if (workouts.isNotEmpty()) {
                for (workout in workouts) {
                    totalReps += workout.totalReps
                    when (workout.exerciseType) {
                        "Bench-press" -> {
                            benchCounter += 1
                        }
                        "Deadlifts" -> {
                            deadliftCounter += 1
                        }
                        "Squats" -> {
                            squatCounter += 1
                        }
                    }

                    if (checkSetFailure(workout.reasonSet1)) {
                        reason1Count += 1
                        failList.add(workout.reasonSet1)
                        if (reason1Count > maxFailure) {
                            maxFailure = reason1Count
                            maxFailureStr = "Set 1"
                        }
                    }
                    if (checkSetFailure(workout.reasonSet2)) {
                        reason2Count += 1
                        failList.add(workout.reasonSet2)
                        if (reason2Count > maxFailure) {
                            maxFailure = reason2Count
                            maxFailureStr = "Set 2"
                        }
                    }
                    if (checkSetFailure(workout.reasonSet3)) {
                        reason3Count += 1
                        failList.add(workout.reasonSet3)
                        if (reason3Count > maxFailure) {
                            maxFailure = reason3Count
                            maxFailureStr = "Set 3"
                        }
                    }
                    if (checkSetFailure(workout.reasonSet4)) {
                        reason4Count += 1
                        failList.add(workout.reasonSet4)
                        if (reason4Count > maxFailure) {
                            maxFailure = reason4Count
                            maxFailureStr = "Set 4"
                        }
                    }
                    if (checkSetFailure(workout.reasonSet5)) {
                        reason5Count += 1
                        failList.add(workout.reasonSet5)
                        if (reason5Count > maxFailure) {
                            maxFailure = reason5Count
                            maxFailureStr = "Set 5"
                        }
                    }
                }
                if (failList.isNotEmpty()) {
                    val mostCommonFailure = failList.groupingBy { it }
                        .eachCount()
                        .toList()
                        .sortedByDescending { it.second }
                        .take(1)
                    fragBinding.mainFailure.setText(mostCommonFailure[0].first)
                }
                val decformat = DecimalFormat("#.##")
                decformat.roundingMode = RoundingMode.CEILING
                fragBinding.totalWorkouts.setText(totalWorkouts.toString())
                fragBinding.benchCounter.setText(benchCounter.toString())
                fragBinding.deadliftCounter.setText(deadliftCounter.toString())
                fragBinding.squatCounter.setText(squatCounter.toString())
                fragBinding.totalReps.setText(totalReps.toString())
                fragBinding.benchCounterPcnt.setText((decformat.format(calculatePercentage(benchCounter, totalWorkouts))).toString()+"%")
                fragBinding.deadliftCounterPcnt.setText((decformat.format(calculatePercentage(deadliftCounter, totalWorkouts))).toString()+"%")
                fragBinding.squatCounterPcnt.setText((decformat.format(calculatePercentage(squatCounter, totalWorkouts))).toString()+"%")
                fragBinding.mainFailureSet.setText(maxFailureStr)
            }



        })

        setSwipeRefresh()
        return root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }


    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Loading Dashboard")
            dashbordViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Loading dashboard")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                dashbordViewModel.liveFirebaseUser.value = firebaseUser
                dashbordViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}