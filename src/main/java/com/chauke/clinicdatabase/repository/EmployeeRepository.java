package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
