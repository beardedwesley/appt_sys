package apptSys.model;

import java.time.*;

public class TimeKeeper {

    public static LocalDateTime convertToLocal(LocalDateTime data) {
        OffsetDateTime zoned = OffsetDateTime.of(data, ZoneOffset.of(ZoneId.of("+00:00").getId()));
        return  zoned.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertToDataZ(LocalDateTime data) {
        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime zoneData = OffsetDateTime.ofInstant(data.atZone(zoneId).toInstant(), zoneId);
        ZonedDateTime zoned = zoneData.atZoneSameInstant(ZoneId.of("+00:00"));
        return zoned.toLocalDateTime();
    }

}


//(ZoneOffset.of(ZoneId.systemDefault().getId())