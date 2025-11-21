package com.example.bak.global.common.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@UtilityClass
public class DateUtils {
    public final static ZoneId zoneId = ZoneId.of("Asia/Seoul");

    public static Date getNow() {
        Instant nowUtc = Instant.now();
        ZonedDateTime now = nowUtc.atZone(zoneId);
        return Date.from(now.toInstant());
    }

    public static Date getExpirationDate(Long ttl){
        if(ttl == null){
            return null;
        }
        Instant nowUtc = Instant.now().plusMillis(ttl);
        ZonedDateTime now = nowUtc.atZone(zoneId);
        return Date.from(now.toInstant());
    }

}
