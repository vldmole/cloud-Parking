package spring.cloudparking.system.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.cloudparking.system.exception.ParkingExceptionFactory;
import spring.cloudparking.system.parking.billing.BillingType;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.repository.ParkingRepository;
import spring.cloudparking.system.parking.billing.Bill;
import spring.cloudparking.system.parking.billing.BillCalculator;
import spring.cloudparking.system.parking.billing.factory.BillCalculatorFactory;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Parking deleteById(Long id)
    {
        Optional<Parking> optional = repository.findById(id);
        if(optional.isEmpty())
            throw parkingExceptionFactory.createNotFoundException(Parking.class.getName(),"id", id);

        this.repository.deleteById(id);

        return optional.get();
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
    public Parking checkout(@NonNull Long id)
    {
        Parking parking = this.getById(id);
        parking.setExit(LocalDateTime.now());

        BillCalculator billCalculator = this.billCalculatorFactory.getBillCalculator(parking.getBillingType());

        Bill bill = billCalculator.getBillFor(parking.getEntry(), parking.getExit());
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
