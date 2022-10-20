package spring.cloudparking.system.parking.billing.factory;

import org.springframework.stereotype.Component;
import spring.cloudparking.system.parking.billing.BillingType;
import spring.cloudparking.system.parking.billing.factory.impl.MonthBilling;
import spring.cloudparking.system.parking.billing.BillCalculator;
import spring.cloudparking.system.parking.billing.factory.impl.DayBilling;
import spring.cloudparking.system.parking.billing.factory.impl.HourBilling;

import java.util.Arrays;

@Component
public class BillCalculatorFactory
{
    static private
    BillCalculatorFactory singletonInstance = null;

    //-----------------------------------------------------
    public BillCalculatorFactory getSingleton()
    {
        if(singletonInstance == null)
            singletonInstance = new BillCalculatorFactory();

        return singletonInstance;
    }

    //-------------------------------------------------------
    private BillCalculatorFactory()
    {
    }

    //-----------------------------------------------------------------------------------------


    private
    BillCalculator[] billCalculatorInstances = new BillCalculator[BillingType.values().length];

    { Arrays.fill(billCalculatorInstances, null); }

    //-----------------------------------------------------------------------------------
    @FunctionalInterface
    private interface Factory
    {
        BillCalculator create();
    }
    //-----------------------------------------------------------------------------------
    static private
    Factory[] factories = {
        ()-> new HourBilling(),
        ()-> new DayBilling(),
        ()-> new MonthBilling()
    };

    //-----------------------------------------------------------------------------------
    public BillCalculator getBillCalculator(BillingType type)
    {
        if(billCalculatorInstances[type.ordinal()] == null)
            billCalculatorInstances[type.ordinal()] = factories[type.ordinal()].create();

        return billCalculatorInstances[type.ordinal()];
    }
}
