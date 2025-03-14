package com.chauke.clinicdatabase.config;



import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import com.chauke.clinicdatabase.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    private final EmployeeRepository employeeRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> employeeRepository.findEmployeeByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository pr, EmployeeRepository epr, PasswordEncoder passwordEncoder) {
        return _ -> {
            Patient george = new Patient("George", LocalDate.of(2001,5,21), "02549494998", "george@email.com",
                    "20 sing street", "0304494990490", "Male");
            pr.save(george);

            Employee linda = new Employee("Linda", "Simmons", "linda@admin.com", passwordEncoder.encode("pass123"), Roles.ADMIN);
            Employee admin = new Employee("Paul", "Doe", "paul@email.com", passwordEncoder.encode("pass123"), Roles.GENERAL);
            epr.save(linda);
            epr.save(admin);
        };
    }
}
