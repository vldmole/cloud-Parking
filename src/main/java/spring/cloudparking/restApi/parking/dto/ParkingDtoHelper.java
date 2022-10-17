package spring.cloudparking.restApi.parking.dto;

import org.springframework.stereotype.Component;
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
        parking.setBill(dto.getBill());
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
        dto.setBill(parking.getBill());
        dto.setEntry(parking.getEntry());
        dto.setExit(parking.getExit());

        return dto;
    }

    public List<ParkingDto> dtoFromParking(List<Parking> lstParking)
    {
        List<ParkingDto> lstDto = new ArrayList<>(lstParking.size());

        lstParking.forEach(parking -> lstDto.add(dtoFromParking(parking)));

       // lstDto = lstParking.stream().map(parking -> dtoFromParking(parking)).collect(Collectors.toList());

        return lstDto;
    }

    public Parking parkingFromDto(CreateParkingDto dto)
    {
        Parking parking = new Parking();

        parking.setModel(dto.getModel());
        parking.setLicense(dto.getLicense());
        parking.setState(dto.getState());
        parking.setColor(dto.getColor());

        return parking;
    }
}
