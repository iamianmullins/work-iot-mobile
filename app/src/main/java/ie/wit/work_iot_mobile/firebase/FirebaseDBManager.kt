package ie.wit.work_iot_mobile.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.work_iot_mobile.models.WorkoutModel
import ie.wit.work_iot_mobile.models.WorkoutStore
import timber.log.Timber

var database: DatabaseReference = FirebaseDatabase.getInstance().reference

object FirebaseDBManager : WorkoutStore {
    override fun findAll(userid: String, workoutList: MutableLiveData<List<WorkoutModel>>) {

        database.child("user-workouts").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Workout error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<WorkoutModel>()
                    val children = snapshot.children
                    children.forEach {
                        val workout = it.getValue(WorkoutModel::class.java)
                        localList.add(workout!!)
                    }
                    database.child("user-workouts").child(userid)
                        .removeEventListener(this)

                    workoutList.value = localList
                }
            })
    }

    override fun findById(userid: String, workoutId: String, workout: MutableLiveData<WorkoutModel>) {
        database.child("user-workouts").child(userid)
            .child(workoutId).get().addOnSuccessListener {
                workout.value = it.getValue(WorkoutModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, workout: WorkoutModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("workouts").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        workout.uid = key
        val workoutValues = workout.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/workouts/$key"] = workoutValues
        childAdd["/user-workouts/$uid/$key"] = workoutValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, workoutId: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/workouts/$workoutId"] = null
        childDelete["/user-workouts/$userid/$workoutId"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, workoutId: String, workout: WorkoutModel) {
        val workoutValues = workout.toMap()
        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["workouts/$workoutId"] = workoutValues
        childUpdate["user-workouts/$userid/$workoutId"] = workoutValues

        database.updateChildren(childUpdate)
    }
}