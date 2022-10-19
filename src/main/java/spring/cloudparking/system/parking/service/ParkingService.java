package spring.cloudparking.system.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.cloudparking.system.exception.ParkingExceptionFactory;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.repository.ParkingRepository;
import spring.cloudparking.system.parking.billing.Bill;
import spring.cloudparking.system.parking.billing.BillCalculator;
import spring.cloudparking.system.parking.billing.factory.BillCalculatorFactory;
import spring.cloudparking.system.parking.billing.factory.BillCalculatorFactory.BillingType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService
{
    ParkingRepository repository;
    BillCalculatorFactory billCalculatorFactory;
    ParkingExceptionFactory parkingExceptionFactory;

    //-------------------------------------------------------------------------------------
    @Autowired
    public ParkingService(ParkingRepository repository,
                          BillCalculatorFactory billCalculatorFactory,
                          ParkingExceptionFactory parkingExceptionFactory)
    {
        this.repository = repository;
        this.billCalculatorFactory = billCalculatorFactory;
        this.parkingExceptionFactory = parkingExceptionFactory;
    }

    //-------------------------------------------------------------------------------------
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public
    List<Parking> findAll()
    {
        return repository.findAll();
    }

    //-------------------------------------------------------------------------------------
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public
    Parking getById(Long id)
    {
        Optional<Parking> optional = repository.findById(id);

        if(optional.isEmpty())
            throw this.parkingExceptionFactory.createNotFoundException("Parking", "id", id);

        return optional.get();
    }

    //-------------------------------------------------------------------------------------
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public
    boolean existsById(@NonNull Long id)
    {
        return repository.existsById(id);
    }

    //-------------------------------------------------------------------------------------
    @Transactional(isolation = Isolation.DEFAULT)
    public
    Parking updateById(@NonNull Long id, @NonNull Parking parking)
    {
        Parking oldParking = this.getById(id);

        oldParking.setEntry(parking.getEntry());
        oldParking.setColor(parking.getColor());
        oldParking.setState(parking.getState());
        oldParking.setModel(parking.getModel());
        oldParking.setBillDescription(parking.getBillDescription());
        oldParking.setBillValue(parking.getBillValue());

        return repository.save(oldParking);
    }

    //-------------------------------------------------------------------------------------
    @Transactional(isolation = Isolation.DEFAULT)
    public
    Parking checkin(@NonNull Parking parking)
    {
        parking.setEntry(LocalDateTime.now());

        return repository.save(parking);
    }

    //-------------------------------------------------------------------------------------
    @Transactional
    public
    Parking hourlyCheckout(@NonNull Long id)
    {
        BillCalculator billCalculator =
                billCalculatorFactory.getBillCalculator(BillingType.HOURLY_BILLING);

        final float currentHourTax = 3.0f;
        final float currentAdditionalHourTax = 1.5f;
        billCalculator.setBaseTax(currentHourTax);
        billCalculator.setAdditionalTax(currentAdditionalHourTax);

        return doCheckout(id, billCalculator);
    }

    //-------------------------------------------------------------------------------------
    @Transactional
    public
    Parking dailyCheckout(@NonNull Long id)
    {
        BillCalculator billCalculator =
                billCalculatorFactory.getBillCalculator(BillingType.DAILY_BILLING);

        final float currentDayTax = 20.0f;
        final float currentAdditionalHourTax = 1.5f;
        billCalculator.setBaseTax(currentDayTax);
        billCalculator.setAdditionalTax(currentAdditionalHourTax);

        return doCheckout(id, billCalculator);
    }

    //-------------------------------------------------------------------------------------
    @Transactional
    public
    Parking monthlyCheckout(@NonNull Long id)
    {
        BillCalculator billCalculator =
                billCalculatorFactory.getBillCalculator(BillingType.MONTHLY_BILLING);

        final float currentMonthTax = 300.0f;
        final float currentAdditionalDayTax = 15.0f;
        billCalculator.setBaseTax(currentMonthTax);
        billCalculator.setAdditionalTax(currentAdditionalDayTax);

        return doCheckout(id, billCalculator);
    }

    //-------------------------------------------------------------------------------------
    private Parking doCheckout(@NonNull Long id, BillCalculator billCalculatorStrategy)
    {
        Parking parking = this.getById(id);

        parking.setExit(LocalDateTime.now());

        Bill bill = billCalculatorStrategy.getBillFor(parking.getEntry(), parking.getExit());
        parking.setBillValue(bill.getValue());

        StringBuilder builder = new StringBuilder();
        Map<String, Float> map = bill.getDescription();
        for(Map.Entry<String, Float> entry : map.entrySet())
        {
            String itemDescription = String.format("%s R$: %.2f\n",entry.getKey(), entry.getValue());
            builder.append(itemDescription);
        }
        parking.setBillDescription(builder.toString());

        return repository.save(parking);
    }
}
