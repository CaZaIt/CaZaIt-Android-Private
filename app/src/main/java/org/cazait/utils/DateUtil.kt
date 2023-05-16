package org.cazait.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    val yyyy_MM_dd = SimpleDateFormat("yyyy.MM.dd")
    val dbDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val serverDateFormat = SimpleDateFormat("yyyy-MM=dd'T'HH:mm:ss.SSS")

    fun convertPrintDateString(date: Date? = Date()) = yyyy_MM_dd.format(date)
}