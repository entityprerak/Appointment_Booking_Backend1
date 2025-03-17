package com.example.hospital.controller;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.entitities.Slot;
import com.example.hospital.service.SlotService;

@RestController
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    // Fetch available slots for a doctor on a given date
    @GetMapping("/available/{doctorId}/{date}")
    public Collection<Slot> getAvailableSlots(@PathVariable Long doctorId, @PathVariable LocalDate date) {
        return slotService.getAvailableSlots(doctorId, date);
    }
}
