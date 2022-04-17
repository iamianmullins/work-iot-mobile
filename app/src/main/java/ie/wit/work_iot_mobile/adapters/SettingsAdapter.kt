package ie.wit.work_iot_mobile.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.CardSettingsBinding
import ie.wit.work_iot_mobile.models.SettingsModel

interface SettingsClickListener {
    fun onSettingsClick(settings: SettingsModel)
}

class SettingsAdapter(private var settings: ArrayList<SettingsModel>,
                      private val listener: SettingsClickListener
)
    : RecyclerView.Adapter<SettingsAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSettingsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val settings = settings[holder.adapterPosition]
        holder.bind(settings,listener)
    }

    fun removeAt(position: Int) {
        settings.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = settings.size

    inner class MainHolder(val binding : CardSettingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userSetting: SettingsModel,  listener: SettingsClickListener) {
            binding.root.tag = userSetting
            binding.userSetting = userSetting
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onSettingsClick(userSetting) }
            binding.executePendingBindings()
        }
    }
}