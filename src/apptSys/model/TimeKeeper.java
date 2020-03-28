package apptSys.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeKeeper {
    private ZoneOffset userOffset;

    public TimeKeeper(ZoneId userZone) {
        this.userOffset = ZoneOffset.of(userZone.getId());
    }

    public TimeKeeper() {
        this.userOffset = ZoneOffset.of(ZoneId.systemDefault().getId());
    }

    public LocalDateTime convertToLocal(LocalDateTime data) {
        return data.plusSeconds(userOffset.getTotalSeconds());
    }

    public LocalDateTime convertToDataZ(LocalDateTime data) {
        return data.minusSeconds(userOffset.getTotalSeconds());
    }

}
