package ie.wit.work_iot_mobile.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun getTime(): String {
    val local = LocalDateTime.now()

    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val currentTime = local.format(format)

    return currentTime
}