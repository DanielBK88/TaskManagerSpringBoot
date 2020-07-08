package daniil.tm2;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Tm2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tm2Application.class, args);
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
