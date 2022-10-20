package spring.cloudparking.system.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.cloudparking.system.facade.parking.dto.CreateParkingDto;
import spring.cloudparking.system.facade.parking.dto.ParkingDto;
import spring.cloudparking.system.facade.parking.dto.ParkingDtoHelper;
import spring.cloudparking.system.parking.billing.BillingType;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.service.ParkingService;

import java.util.List;

@Service
public class ParkingFacade
{
    ParkingService parkingService;
    ParkingDtoHelper parkingDtoHelper = new ParkingDtoHelper();

    @Autowired
    public ParkingFacade(ParkingService parkingService)
    {
        this.parkingService = parkingService;
    }

    public ParkingDto getById(Long id)
    {
        Parking parking = parkingService.getById(id);

        return parkingDtoHelper.dtoFromParking(parking);
    }

    public List<ParkingDto> findAll()
    {
        List<Parking> parkingList = parkingService.findAll();

        return parkingDtoHelper.dtoFromParking(parkingList);
    }

    public ParkingDto updateById(Long id, ParkingDto parkingDto)
    {
        Parking parking = parkingDtoHelper.parkingFromDto(parkingDto);

        Parking updatedParking = parkingService.updateById(id, parking);

        return parkingDtoHelper.dtoFromParking(updatedParking);
    }

    public ParkingDto deleteById(Long id)
    {
        Parking deletedParking = parkingService.deleteById(id);

        return parkingDtoHelper.dtoFromParking(deletedParking);
    }

    public ParkingDto checkin(CreateParkingDto createParkingDto)
    {
        Parking checkinParking = parkingDtoHelper.parkingFromDto(createParkingDto);

        Parking newlyParking = parkingService.checkin(checkinParking);

        return parkingDtoHelper.dtoFromParking(newlyParking);
    }

    public ParkingDto checkout(Long id)
    {
        Parking parking =  this.parkingService.checkout(id);

        return parkingDtoHelper.dtoFromParking(parking);
    }

    public ParkingDto checkout(Parking parking)
    {
        Parking checkoutParking =  this.parkingService.checkout(parking.getId());

        return parkingDtoHelper.dtoFromParking(checkoutParking);
    }
    public String[] getBillingTypes()
    {
        return BillingType.getNames();
    }
}
