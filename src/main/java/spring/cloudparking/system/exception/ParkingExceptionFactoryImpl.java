package spring.cloudparking.system.exception;

import org.springframework.stereotype.Component;

@Component
class ParkingExceptionFactoryImpl implements ParkingExceptionFactory
{
	@Override
	public <T> ParkingException createNotFoundException(String entityClassName, String searchFieldName, T searchValue)
	{
		final String format = "Not Found Error: Searching into '%s' on field '%s' for value '%s' ";
		
		return new ParkingException(String.format(format, entityClassName, searchFieldName, searchValue));
	}
}
