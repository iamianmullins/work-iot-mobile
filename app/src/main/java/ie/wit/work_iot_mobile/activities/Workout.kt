package ie.wit.work_iot_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.databinding.ActivityWorkoutBinding
import ie.wit.work_iot_mobile.main.WorkIOTApp
import ie.wit.work_iot_mobile.models.WorkiotModel
import timber.log.Timber

class Workout : AppCompatActivity() {

    private lateinit var workoutLayout: ActivityWorkoutBinding
    lateinit var app: WorkIOTApp
    var totalRepCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        workoutLayout = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(workoutLayout.root)
        app = this.application as WorkIOTApp

        val reasons = resources.getStringArray(R.array.reasons)
        val repPickerCurrentArray = IntArray(5)

        workoutLayout.repPicker1.minValue = 0
        workoutLayout.repPicker1.maxValue = 25
        workoutLayout.reasonPicker1.minValue = 0
        workoutLayout.reasonPicker1.maxValue = reasons.size-1
        workoutLayout.reasonPicker1.displayedValues= reasons

        workoutLayout.repPicker1.setOnValueChangedListener { _, _, newVal ->
            repPickerCurrentArray[0] = newVal
            //Display the newly selected number to repCounter
            var one: Int = repPickerCurrentArray.sum()
            workoutLayout.repCounter.setText("$one")
        }

        workoutLayout.createButton.setOnClickListener {
            val totalReps = if (workoutLayout.repCounter.text.isNotEmpty())
                workoutLayout.repCounter.text.toString()
                    .toInt() else workoutLayout.repPicker1.value
                val exerciseType =
                    if (workoutLayout.exerciseType.checkedRadioButtonId == R.id.Benchpress)
                        "Bench-press"
                    else if (workoutLayout.exerciseType.checkedRadioButtonId == R.id.Deadlift)
                        "Deadlifts"
                    else "Squats"
                val repsSet1 = workoutLayout.repPicker1.toString()
                val reasonSet1 = workoutLayout.reasonPicker1.toString()

                totalRepCount += totalReps
                workoutLayout.progressBar.progress = totalRepCount
                app.workoutsStore.create(
                    WorkiotModel(
                        exerciseType = exerciseType,
                        totalReps = totalReps,
                        repsSet1 = repsSet1,
                        reasonSet1 = reasonSet1
                    )
                )
                Timber.i("Total amount of reps entered $totalRepCount")
        }
    }

    override fun onResume() {
        super.onResume()
        totalRepCount = app.workoutsStore.findAll().sumOf { it.totalReps }
        workoutLayout.progressBar.progress = totalRepCount
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_workout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_report -> {
                startActivity(
                    Intent(
                        this,
                        Report::class.java
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}