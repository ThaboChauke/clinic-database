package com.chauke.clinicdatabase;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import com.chauke.clinicdatabase.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ClinicdatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicdatabaseApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PatientRepository pr, EmployeeRepository epr) {
		return _ -> {
			Patient george = new Patient("George", LocalDate.of(2001,5,21), "02549494998", "george@email.com",
					"20 sing street", "0304494990490", "Male");
			pr.save(george);

			Employee linda = new Employee("Linda", "Simmons", "linda@email.com", "pass123", Roles.GENERAL);
			epr.save(linda);
		};
	}

}
