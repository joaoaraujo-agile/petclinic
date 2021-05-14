package com.agile.petclinic.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.entities.enums.AppointmentType;
import com.agile.petclinic.services.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AppointmentResource.class)
class AppointmentResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppointmentService service;

	@Autowired
	ObjectMapper Obj;

	static Pet pet1;
	static Pet pet2;
	static Pet pet3;
	
	static Appointment ap1;
	static Appointment ap2;
	static Appointment ap3;

	@BeforeAll
	static void initAll() {
		pet1 = new Pet(1L, null, null, null, null);
		pet2 = new Pet(2L, null, null, null, null);
		pet3 = new Pet(3L, null, null, null, null);
		
		ap1 = new Appointment(1L, Instant.parse("2021-05-13T12:00:00Z"), "Routine exam", AppointmentType.MEDICAL, pet1, null, null);
		ap2 = new Appointment(2L, Instant.parse("2021-05-14T11:00:00Z"), "Surgery", AppointmentType.MEDICAL, pet2, null, null);
		ap3 = new Appointment(3L, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming", AppointmentType.GROOMING, pet3, null, null);
	}

	@Test
	void getAllMethod() throws Exception {
		List<Appointment> list = Arrays.asList(ap1, ap2, ap3);

		when(service.findAll()).thenReturn(list);

		this.mockMvc.perform(get("/appointments")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(list)));
	}
	
	@Test
	void getOneMethod() throws Exception {
		Long id = ap1.getId();
		
		when(service.findById(id)).thenReturn(ap1);

		this.mockMvc.perform(get("/appointments/" + id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(ap1)));
	}
	
	@Test
	void postMethod() throws Exception {
		Appointment obj = new Appointment(4L, Instant.parse("2021-05-13T13:00:00Z"), "Shower and Grooming", AppointmentType.GROOMING, pet3, null, null);

		when(service.insert(obj)).thenReturn(obj);
		
		this.mockMvc.perform(post("/appointments").contentType("application/json").content(Obj.writeValueAsString(obj))).andDo(print()).andExpect(status().isCreated())
		.andExpect(content().json(Obj.writeValueAsString(obj)));
	}

	@Test
	void deleteMethod() throws Exception {		
		this.mockMvc.perform(delete("/appointments/" + 1L)).andDo(print()).andExpect(status().isNoContent());
	}
	
	@Test
	void putMethod() throws Exception {
		Long id = 3L;
		
		Appointment obj = new Appointment(id, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming", AppointmentType.GROOMING, pet2, null, null);

		when(service.update(id, obj)).thenReturn(obj);
		
		this.mockMvc.perform(put("/appointments/" + id).contentType("application/json").content(Obj.writeValueAsString(obj))).andDo(print()).andExpect(status().isOk())
		.andExpect(content().json(Obj.writeValueAsString(obj)));
	}
}
