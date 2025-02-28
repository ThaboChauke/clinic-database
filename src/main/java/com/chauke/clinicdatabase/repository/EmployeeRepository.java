package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findEmployeeByEmail(String email);

    boolean existsByEmail(String email);

    void removeByEmail(String email);

}
