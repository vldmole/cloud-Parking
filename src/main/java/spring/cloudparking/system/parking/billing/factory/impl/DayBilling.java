package spring.cloudparking.system.parking.billing.factory.impl;

import spring.cloudparking.system.parking.billing.Bill;
import spring.cloudparking.system.parking.billing.BillCalculator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DayBilling implements BillCalculator
{
    private float firstDayTax;
    private float additionalDayTax;

    @Override
    public void setBaseTax(float baseTax)
    {
        this.firstDayTax = baseTax;
    }

    @Override
    public void setAdditionalTax(float additionalTax)
    {
        this.additionalDayTax = additionalTax;
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public Bill getBillFor(LocalDateTime startTime, LocalDateTime endTime)
    {
        SimpleBill bill = new SimpleBill(startTime, endTime);

        return calculateBill(bill);
    }

    //---------------------------------------------------------------------------------------------------
    private Bill calculateBill(SimpleBill bill)
    {
        long completeDays = ChronoUnit.DAYS.between(bill.getStartDateTime(), bill.getEndDateTime());

        LocalDateTime completeDaysDateTime = bill.getStartDateTime().plus(completeDays, ChronoUnit.DAYS);
        long additionalHours = ChronoUnit.HOURS.between(completeDaysDateTime, bill.getEndDateTime());

        bill.addDescriptionItem("First day", this.firstDayTax);

        completeDays -= 1;
        if(additionalHours > 2)
            completeDays += 1;

        if(completeDays > 0)
            bill.addDescriptionItem(String.format("Additional days '%d'", completeDays), this.additionalDayTax);

        return bill;
    }

    //------------------------------------------------------
    public float getFirstDayTax()
    {
        return firstDayTax;
    }

    //------------------------------------------------------
    public void setFirstDayTax(float firstDayTax)
    {
        this.firstDayTax = firstDayTax;
    }

    //------------------------------------------------------
    public float getAdditionalDayTax()
    {
        return additionalDayTax;
    }

    //------------------------------------------------------
    public void setAdditionalDayTax(float additionalDayTax)
    {
        this.additionalDayTax = additionalDayTax;
    }
}
