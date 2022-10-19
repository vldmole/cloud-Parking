package spring.cloudparking.system.parking.billing.factory;

import org.springframework.stereotype.Component;
import spring.cloudparking.system.parking.billing.factory.impl.MonthlyBilling;
import spring.cloudparking.system.parking.billing.BillCalculator;
import spring.cloudparking.system.parking.billing.factory.impl.DailyBilling;
import spring.cloudparking.system.parking.billing.factory.impl.HourlyBilling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;

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
    public enum
    BillingType {HOURLY_BILLING, DAILY_BILLING, MONTHLY_BILLING};

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
        ()-> new HourlyBilling(),
        ()-> new DailyBilling(),
        ()-> new MonthlyBilling()
    };

    //-----------------------------------------------------------------------------------
    public BillCalculator getBillCalculator(BillingType type)
    {
        if(billCalculatorInstances[type.ordinal()] == null)
            billCalculatorInstances[type.ordinal()] = factories[type.ordinal()].create();

        return billCalculatorInstances[type.ordinal()];
    }
}
