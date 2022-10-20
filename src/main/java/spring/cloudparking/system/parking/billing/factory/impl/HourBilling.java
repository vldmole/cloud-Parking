package spring.cloudparking.system.parking.billing.factory.impl;

import spring.cloudparking.system.parking.billing.Bill;
import spring.cloudparking.system.parking.billing.BillCalculator;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourBilling implements BillCalculator
{
    private float firstHourTax;
    private float additionalHourTax;

    @Override
    public void setBaseTax(float baseTax)
    {
        this.firstHourTax = baseTax;
    }

    @Override
    public void setAdditionalTax(float additionalTax)
    {
        this.additionalHourTax = additionalTax;
    }

    //----------------------------------------------------------------------------------------
    @Override
    public Bill getBillFor(LocalDateTime startTime, LocalDateTime endTime)
    {
        SimpleBill bill = new SimpleBill(startTime, endTime);

        return calculateBill(bill);
    }

    //----------------------------------------------------------------------------------------
    private Bill calculateBill(SimpleBill bill)
    {
        Duration duration = Duration.between(bill.getStartDateTime(), bill.getEndDateTime());

        Long minutes = duration.toMinutes();
        bill.addDescriptionItem("First Hour", this.firstHourTax);

        minutes -= 60;
        if (minutes > 10)
        {
            float additionalValue = (minutes / 15) * additionalHourTax / 4;
            bill.addDescriptionItem("Additional Time", additionalValue);
        }

        return bill;
    }

    //-------------------------------------------------------
    public float getFirstHourTax()
    {
        return firstHourTax;
    }

    //-------------------------------------------------------
    public void setFirstHourTax(float firstHourTax)
    {
        this.firstHourTax = firstHourTax;
    }

    //-------------------------------------------------------
    public float getAdditionalHourTax()
    {
        return additionalHourTax;
    }

    //-------------------------------------------------------
    public void setAdditionalHourTax(float additionalHourTax)
    {
        this.additionalHourTax = additionalHourTax;
    }
}
