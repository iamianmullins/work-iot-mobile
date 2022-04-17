package ie.wit.work_iot_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.CardWorkoutBinding
import ie.wit.work_iot_mobile.models.WorkoutModel
import ie.wit.work_iot_mobile.utils.customTransformation

interface WorkoutClickListener {
    fun onWorkoutClick(workout: WorkoutModel)
}

class WorkoutAdapter constructor(private var workouts: ArrayList<WorkoutModel>,
                                 private val listener: WorkoutClickListener,
                                 private val readOnly: Boolean)
    : RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWorkoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
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

    inner class MainHolder(val binding : CardWorkoutBinding, private val readOnly: Boolean)
        : RecyclerView.ViewHolder(binding.root) {
        val readOnlyRow = readOnly
        fun bind(workout: WorkoutModel,  listener: WorkoutClickListener) {
            binding.root.tag = workout
            binding.workout = workout
            Picasso.get().load(workout.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onWorkoutClick(workout) }
            binding.executePendingBindings()
        }
    }
}