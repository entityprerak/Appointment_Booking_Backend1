package com.example.hospital.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.example.hospital.entitities.Slot;

public interface SlotService {

	Collection<Slot> getAvailableSlots(Long doctorId, LocalDate date);

	Slot assignSlotToDoctor(Long doctorId, Slot slot);
	
    Slot addDoctorSlot(Long doctorId, Slot slot);

    // ✅ Fetch all slots created by a specific doctor
    List<Slot> getDoctorSlots(Long doctorId);

}
