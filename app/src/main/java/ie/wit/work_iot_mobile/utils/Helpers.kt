package ie.wit.work_iot_mobile.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Transformation
import ie.wit.work_iot_mobile.R
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun createLoader(activity: FragmentActivity) : AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true) // 'false' if you want user to wait
        .setView(R.layout.loading)
    var loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.mipmap.ic_launcher_round)

    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing) {
        loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing)
        loader.dismiss()
}

fun serviceUnavailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Workout Service Unavailable. Try again later",
        Toast.LENGTH_LONG
    ).show()
}

fun serviceAvailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Workout Service Contacted Successfully",
        Toast.LENGTH_LONG
    ).show()
}

fun customTransformation() : Transformation =
    RoundedTransformationBuilder()
        .borderColor(Color.WHITE)
        .borderWidthDp(2F)
        .cornerRadiusDp(35F)
        .oval(false)
        .build()

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_profile_image.toString())
    intentLauncher.launch(chooseFile)
}

fun readImageUri(resultCode: Int, data: Intent?): Uri? {
    var uri: Uri? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try { uri = data.data }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return uri
}


fun getTime(): String {
    val local = LocalDateTime.now()

    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val currentTime = local.format(format)

    return currentTime
}


fun getGoalStr(goalPos: Int): String {
    var goalStr = ""
    if (goalPos == 0){
        goalStr = "Endurance"
    }
    else if (goalPos == 1){
        goalStr = "Hypertrophy"
    }
    else {
        goalStr = "Strength"
    }
    return goalStr
}

fun getGoalPos(goalStr: String): Int {
    var goalInt = 0
    if (goalStr == "Endurance"){
        goalInt = 0
    }
    else if (goalStr == "Hypertrophy"){
        goalInt =1
    }
    else {
        goalInt = 2
    }
    return  goalInt
}

fun getTypeStr(typePos: Int): String {
    var typeStr = ""
    if (typePos == 0){
        typeStr = "Bench-Press"
    }
    else if (typePos == 1){
        typeStr = "Deadlifts"
    }
    else {
        typeStr = "Squats"
    }
    return typeStr
}

fun getTypePos(typeStr: String): Int {
    var typeInt = 0
    if (typeStr == "Bench-Press"){
        typeInt = 0
    }
    else if (typeStr == "Deadlifts"){
        typeInt = 1
    }
    else {
        typeInt = 2
    }
    return  typeInt
}

fun getFailPos(reasonList: MutableList<String>): MutableList<Int> {
   val returnReasons : MutableList<Int> = ArrayList()
    for(reason in reasonList){
        if (reason == "N/A"){
            returnReasons.add(0)
        }
        else if (reason == "Bar-tilt"){
            returnReasons.add(1)
        }
        else if (reason == "Fatigue"){
            returnReasons.add(2)
        }
        else if (reason == "Multiple"){
            returnReasons.add(3)
        }
        else {
            returnReasons.add(4)
        }
    }
    return returnReasons
}

fun getReasonStr(typePos: Int): String {
    var typeStr = ""
    if (typePos == 0){
        typeStr = "N/A"
    }
    else if (typePos == 1){
        typeStr = "Bar-tilt"
    }
    else if (typePos == 2){
        typeStr = "Fatigue"
    }
    else {
        typeStr = "Multiple"
    }
    return typeStr
}


fun calculatePercentage(value: Int, total: Int): Float {
    val percentage: Float = (value.toFloat() / total) * 100
    return  percentage
}

fun checkSetFailure (reason: String): Boolean{
    val failList = listOf("Bar-tilt", "Fatigue"
        //, "Multiple", "Other"
        )
    var fail = false
    if(reason in failList){
        fail = true
    }
    return fail
}
