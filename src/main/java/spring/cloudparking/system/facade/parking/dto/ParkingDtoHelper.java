package spring.cloudparking.system.facade.parking.dto;

import org.springframework.stereotype.Component;
import spring.cloudparking.system.parking.billing.BillingType;
import spring.cloudparking.system.parking.model.Parking;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingDtoHelper
{
    public Parking parkingFromDto(ParkingDto dto)
    {
        //could use ModelMapper
        Parking parking = new Parking();

        parking.setId(dto.getId());
        parking.setModel(dto.getModel());
        parking.setLicense(dto.getLicense());
        parking.setState(dto.getState());
        parking.setColor(dto.getColor());
        parking.setBillingType(BillingType.valueOf(dto.getBillingTypeName()));
        parking.setEntry(dto.getEntry());
        parking.setExit(dto.getExit());

        return parking;
    }

    public ParkingDto dtoFromParking(Parking parking)
    {
        ParkingDto dto = new ParkingDto();

        dto.setId(parking.getId());
        dto.setModel(parking.getModel());
        dto.setLicense(parking.getLicense());
        dto.setState(parking.getState());
        dto.setColor(parking.getColor());
        dto.setBillingTypeName(parking.getBillingType().name());
        dto.setBillValue( parking.getBillValue());
        dto.setBillDescription(parking.getBillDescription());
        dto.setEntry(parking.getEntry());
        dto.setExit(parking.getExit());

        return dto;
    }

    public List<ParkingDto> dtoFromParking(List<Parking> lstParking)
    {
        final List<ParkingDto> lstDto = new ArrayList<>(lstParking.size());

        lstParking.forEach(parking -> lstDto.add(dtoFromParking(parking)));

        return lstDto;
    }

    public Parking parkingFromDto(CreateParkingDto dto)
    {
        Parking parking = new Parking();

        parking.setModel(dto.getModel());
        parking.setLicense(dto.getLicense());
        parking.setState(dto.getState());
        parking.setColor(dto.getColor());
        parking.setBillingType(BillingType.valueOf(dto.getBillingTypeName()));

        return parking;
    }
}
