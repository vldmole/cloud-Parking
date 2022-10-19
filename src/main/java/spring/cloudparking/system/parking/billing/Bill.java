package spring.cloudparking.system.parking.billing;

import java.time.LocalDateTime;
import java.util.Map;

public interface Bill
{
    LocalDateTime getStartDateTime();
    LocalDateTime getEndDateTime();
    float getValue();
    Map<String, Float> getDescription();
}
