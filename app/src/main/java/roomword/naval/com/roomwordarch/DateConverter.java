package roomword.naval.com.roomwordarch;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * {@link TypeConverter} for long to {@link Date}
 * <p>
 * This stores the date as a long in the database, but returns it as a {@link Date}
 */
public class DateConverter
{
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? new Date(System.currentTimeMillis()) : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? System.currentTimeMillis() : date.getTime();
//        return date == null ? null : date.getTime();
    }
}