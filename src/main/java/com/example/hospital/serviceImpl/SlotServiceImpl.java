package com.example.hospital.serviceImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospital.entitities.Appointment;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.Slot;
import com.example.hospital.service.SlotService;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.Repository.SlotRepository;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Collection<Slot> getAvailableSlots(Long doctorId, LocalDate date) {
        // Fetch the doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));

        // Fetch slots for the doctor on the given date
        Collection<Slot> doctorSlots = slotRepository.findSlotsByDoctorAndDate(doctorId, date);

        // Get all booked appointments for that date
        Collection<Appointment> bookedAppointments = appointmentRepository.getAppointmentByDoctorIdAndDate(doctorId, date);

        // Create a mapping of Slot ID to appointment count for efficiency
        Map<Integer, Long> slotCounts = bookedAppointments.stream()
                .collect(Collectors.groupingBy(a -> a.getSlot().getId(), Collectors.counting()));

        // Filter slots that have fewer than 4 bookings
        return doctorSlots.stream()
                .filter(slot -> slotCounts.getOrDefault(slot.getId(), 0L) < 4) // Only slots with less than 4 bookings
                .collect(Collectors.toList());
    }

    @Override
    public Slot assignSlotToDoctor(Long doctorId, Slot slot) {
        // Fetch the doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));

        // Save slot and assign it to the doctor
        slotRepository.save(slot);
        doctor.getSlots().add(slot);
        doctorRepository.save(doctor);

        return slot;
    }
}
