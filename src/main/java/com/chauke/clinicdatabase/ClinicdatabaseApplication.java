package com.chauke.clinicdatabase;

import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicdatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicdatabaseApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PatientRepository pr) {
		return args -> {
			Patient george = new Patient("George", "21/05/2002", "02549494998", "george@email.com",
					"20 sing street", "0304494990490", "Male");
			pr.save(george);
		};
	}

}
