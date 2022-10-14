package spring.cloudparking.system.exception;

import org.springframework.stereotype.Component;

@Component
public interface ParkingExceptionFactory
{
	public <T> ParkingException createNotFoundException(
			String entityClassName, String searchFieldName, T searchValue);
	
}
