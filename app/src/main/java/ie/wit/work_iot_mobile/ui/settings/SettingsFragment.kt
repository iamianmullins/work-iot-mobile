package ie.wit.work_iot_mobile.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.adapters.SettingsClickListener
import ie.wit.work_iot_mobile.adapters.SettingsAdapter
import ie.wit.work_iot_mobile.databinding.FragmentSettingsBinding
import ie.wit.work_iot_mobile.models.SettingsModel
import ie.wit.work_iot_mobile.ui.auth.LoggedInViewModel
import ie.wit.work_iot_mobile.utils.*

class SettingsFragment : Fragment(), SettingsClickListener {

    private var _fragBinding: FragmentSettingsBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragment2ToWorkoutFragment()
            findNavController().navigate(action)
        }
        showLoader(loader,"Downloading Settings")
        settingsViewModel.observableSettingsList.observe(viewLifecycleOwner, Observer {
                settings ->
            settings?.let {
                render(settings as ArrayList<SettingsModel>)
                hideLoader(loader)
                checkSwipeRefresh()
                if (settings.isEmpty()) {
                    settingsViewModel.addInitialSettings(
                        loggedInViewModel.liveFirebaseUser,
                        SettingsModel(
                            email = loggedInViewModel.liveFirebaseUser.value?.email!!
                        )
                    )
                }
                Toast.makeText(context, "workout Settings successfully added", Toast.LENGTH_SHORT).show()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Wiping Settings")
                val adapter = fragBinding.recyclerView.adapter as SettingsAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                settingsViewModel.delete(settingsViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as SettingsModel).uid)
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSettingsClick(viewHolder.itemView.tag as SettingsModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

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

    private fun render(settingList: ArrayList<SettingsModel>) {
        fragBinding.recyclerView.adapter = SettingsAdapter(settingList,this)
        if (settingList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.settingsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.settingsNotFound.visibility = View.GONE
        }
    }
    override fun onSettingsClick(settings: SettingsModel) {
        val action = SettingsFragmentDirections.actionSettingsFragment2ToSettingsEditFragment(settings.uid)
        findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading settings")
            settingsViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading settings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                settingsViewModel.liveFirebaseUser.value = firebaseUser
                settingsViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}