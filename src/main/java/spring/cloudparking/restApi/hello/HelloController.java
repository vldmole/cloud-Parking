package spring.cloudparking.restApi.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController
{
	@GetMapping
	public String sayHello()
	{
		return "Hello World!";
	}
}
