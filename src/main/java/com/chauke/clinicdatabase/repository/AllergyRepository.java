package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {
}
