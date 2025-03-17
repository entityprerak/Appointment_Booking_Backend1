package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Repository.SpecialisationRepository;
import com.example.hospital.entitities.Specialisation;

import java.util.List;

@RestController
@RequestMapping("/specialities")
public class SpecialisationController {

    @Autowired
    private SpecialisationRepository specialisationRepository;

    @GetMapping
    public List<Specialisation> getAllSpecialities() {
        return specialisationRepository.findAll();
    }
}
