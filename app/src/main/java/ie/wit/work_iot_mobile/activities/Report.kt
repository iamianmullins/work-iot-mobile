package ie.wit.work_iot_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.work_iot_mobile.R
import ie.wit.work_iot_mobile.adapters.WorkoutAdapter
import ie.wit.work_iot_mobile.databinding.ActivityReportBinding
import ie.wit.work_iot_mobile.main.WorkIOTApp

class Report : AppCompatActivity() {

    lateinit var app: WorkIOTApp
    lateinit var reportLayout : ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportLayout = ActivityReportBinding.inflate(layoutInflater)
        setContentView(reportLayout.root)

        app = this.application as WorkIOTApp
        reportLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        reportLayout.recyclerView.adapter = WorkoutAdapter(app.workoutsStore.findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_workout -> { startActivity(
                Intent(this,
                Workout::class.java)
            )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}