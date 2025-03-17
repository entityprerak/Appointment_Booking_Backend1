package com.example.hospital.service;

import java.time.LocalDate;
import java.util.Collection;

import com.example.hospital.entitities.Slot;

public interface SlotService {

	Collection<Slot> getAvailableSlots(Long doctorId, LocalDate date);

	Slot assignSlotToDoctor(Long doctorId, Slot slot);

}
