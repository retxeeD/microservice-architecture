package ms.studies.personmicrosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PersonMicrosserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonMicrosserviceApplication.class, args);
	}

}
