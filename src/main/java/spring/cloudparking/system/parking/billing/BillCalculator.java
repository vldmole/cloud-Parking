package spring.cloudparking.system.parking.billing;

import java.time.LocalDateTime;

public interface BillCalculator
{
    void setBaseTax(float baseTax);
    void setAdditionalTax(float additionalTax);
    Bill getBillFor(LocalDateTime startTime, LocalDateTime endTime);
}
