package louie.dong.airbnb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AirbnbApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.yml,"
		+ "classpath:application-s3.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(AirbnbApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}

}
