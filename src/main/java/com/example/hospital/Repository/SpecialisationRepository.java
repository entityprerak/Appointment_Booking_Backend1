package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospital.entitities.Specialisation;

public interface SpecialisationRepository extends JpaRepository<Specialisation, Integer> {
}
