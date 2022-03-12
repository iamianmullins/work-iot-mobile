package ie.wit.work_iot_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.CardWorkoutBinding
import ie.wit.work_iot_mobile.models.WorkiotModel

class WorkoutAdapter constructor(private var workiots: List<WorkiotModel>)
    : RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWorkoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val workout = workiots[holder.adapterPosition]
        holder.bind(workout)
    }

    override fun getItemCount(): Int = workiots.size

    inner class MainHolder(val binding : CardWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workiot: WorkiotModel) {
            binding.paymentamount.text = workiot.totalReps.toString()
            binding.exerciseType.text = workiot.exerciseType
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}