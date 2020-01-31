package com.fengwk.support.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 
 * @author fengwk
 */
public final class DateUtils {
	
    private static final ZoneId ZONE_ID_SYSTEM = ZoneId.systemDefault();
    private static final ZoneId ZONE_ID_GMT = ZoneId.of("GMT");

	private DateUtils() {}
	
	public static LocalDateTime now(ZoneId zoneId) {
		return ZonedDateTime.now().withZoneSameInstant(zoneId).toLocalDateTime();
	}
	
	public static LocalDateTime gmtToSystemZone(LocalDateTime gmt) {
		if (gmt == null) {
			return null;
		}
		return gmt.atZone(ZONE_ID_GMT).withZoneSameInstant(ZONE_ID_SYSTEM).toLocalDateTime();
	}
	
	/**
     * Date -> LocalDateTime
     * 
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID_SYSTEM);
    }
    
    /**
     * localDateTime -> Date
     * 
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZONE_ID_SYSTEM).toInstant());
    }
    
    /**
     * Date -> LocalDate
     * 
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalDate();
    }
    
    /**
     * LocalDate -> Date
     * 
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZONE_ID_SYSTEM).toInstant());
    }
    
    /**
     * 
     * @param localDateTime
     * @return
     */
    public static long toUnixMs(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID_SYSTEM).toInstant().toEpochMilli();
    }
	
}
