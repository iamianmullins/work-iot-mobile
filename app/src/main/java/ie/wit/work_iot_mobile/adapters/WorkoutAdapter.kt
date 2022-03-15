package ie.wit.work_iot_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.CardWorkoutBinding
import ie.wit.work_iot_mobile.models.WorkoutModel

interface WorkoutClickListener {
    fun onWorkoutClick(workout: WorkoutModel)
}

class WorkoutAdapter constructor(private var workouts: ArrayList<WorkoutModel>,
                                  private val listener: WorkoutClickListener)
    : RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWorkoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val workout = workouts[holder.adapterPosition]
        holder.bind(workout,listener)
    }

    fun removeAt(position: Int) {
        workouts.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = workouts.size

    inner class MainHolder(val binding : CardWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: WorkoutModel,  listener: WorkoutClickListener) {
            binding.root.tag = workout
            binding.workout = workout
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onWorkoutClick(workout) }
            binding.executePendingBindings()
        }
    }
}