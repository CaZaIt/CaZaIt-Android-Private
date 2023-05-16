package org.cazait.data.source.local

import androidx.room.TypeConverter
import org.cazait.utils.DateUtil
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(timeStamp: String?): Date? {
        return timeStamp?.let { DateUtil.dbDateFormat.parse(it) }
    }

    @TypeConverter
    fun toTimeStamp(date: Date?): String? {
        return date?.let { DateUtil.dbDateFormat.format(it) }
    }
}