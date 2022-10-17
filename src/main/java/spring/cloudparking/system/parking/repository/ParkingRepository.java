package spring.cloudparking.system.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.cloudparking.system.parking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long>
{
}
