package ie.wit.work_iot_mobile.ui.report

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.adapters.WorkoutAdapter
import ie.wit.work_iot_mobile.adapters.WorkoutClickListener
import ie.wit.work_iot_mobile.databinding.FragmentReportBinding
import ie.wit.work_iot_mobile.main.WorkIOTApp
import ie.wit.work_iot_mobile.models.WorkoutModel

class ReportFragment : Fragment(), WorkoutClickListener {

    lateinit var app: WorkIOTApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        reportViewModel.observableWorkoutList.observe(viewLifecycleOwner, Observer {
                workouts ->
            workouts?.let { render(workouts) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToWorkoutFragment()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(workoutList: List<WorkoutModel>) {
        fragBinding.recyclerView.adapter = WorkoutAdapter(workoutList,this)
        if (workoutList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.workoutsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.workoutsNotFound.visibility = View.GONE
        }
    }

    override fun onWorkoutClick(workout: WorkoutModel) {
        val action = ReportFragmentDirections.actionReportFragmentToWorkoutFragment()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        reportViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}