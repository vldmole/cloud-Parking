package spring.cloudparking.system.parking.billing.factory.impl;

import spring.cloudparking.system.parking.billing.Bill;
import spring.cloudparking.system.parking.billing.BillCalculator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MonthlyBilling implements BillCalculator
{
    private float monthtlyTax = 0.0f;
    private float additionalDayTax = 0.0f;

    @Override
    public void setBaseTax(float baseTax)
    {
        this.monthtlyTax = baseTax;
        this.additionalDayTax = baseTax /30;
    }

    @Override
    public void setAdditionalTax(float additionalTax)
    {
        this.additionalDayTax = additionalTax;
    }

    @Override
    public Bill getBillFor(LocalDateTime startTime, LocalDateTime endTime)
    {
        SimpleBill bill = new SimpleBill(startTime, endTime);

        return calculateBill(bill);
    }

    private Bill calculateBill(SimpleBill bill)
    {
        long completeMonths = ChronoUnit.MONTHS.between(bill.getStartDateTime(), bill.getEndDateTime());
        if(completeMonths > 0)
        {
            String description = String.format("Complete Months '%d'", completeMonths);
            float value = completeMonths * this.monthtlyTax;
            bill.addDescriptionItem(description, value);
        }

        LocalDateTime completeMonthsDate = bill.getStartDateTime().plusMonths(completeMonths);
        long completeDays = ChronoUnit.DAYS.between(completeMonthsDate, bill.getEndDateTime());

        LocalDateTime completeDaysDate = completeMonthsDate.plusDays(completeDays);
        long additionalhours = ChronoUnit.HOURS.between(completeDaysDate, bill.getEndDateTime());
        if(additionalhours > 6)
            completeDays += 1;

        String description = String.format("Additional days '%d'", completeDays);
        float value = this.monthtlyTax/30 * completeDays;
        bill.addDescriptionItem(description, value);

        return bill;
    }
}
