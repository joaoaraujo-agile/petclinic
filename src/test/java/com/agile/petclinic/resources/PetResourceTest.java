package com.agile.petclinic.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agile.petclinic.entities.Pet;
import com.agile.petclinic.services.PetAppointmentHistoryService;
import com.agile.petclinic.services.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PetResource.class)
class PetResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetService service;

	@MockBean
	private PetAppointmentHistoryService pahService;

	@Autowired
	ObjectMapper Obj;

	static Pet pet1;
	static Pet pet2;
	static Pet pet3;

	@BeforeAll
	static void initAll() {
		pet1 = new Pet(1L, null, null, null, null);
		pet2 = new Pet(2L, null, null, null, null);
		pet3 = new Pet(3L, null, null, null, null);
	}

	@Test
	void getAllMethod() throws Exception {
		List<Pet> list = Arrays.asList(pet1, pet2, pet3);

		when(service.findAll()).thenReturn(list);

		this.mockMvc.perform(get("/pets")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(list)));
	}

	@Test
	void getOneMethod() throws Exception {
		Long id = pet1.getId();

		when(service.findById(id)).thenReturn(pet1);

		this.mockMvc.perform(get("/pets/" + id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(pet1)));
	}

	@Test
	void postMethod() throws Exception {
		Pet obj = new Pet(4L, "Jack", LocalDate.of(2014, Month.JANUARY, 15), "Rottweiler", 'M');

		when(service.insert(obj)).thenReturn(obj);

		this.mockMvc.perform(post("/pets").contentType("application/json").content(Obj.writeValueAsString(obj)))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().json(Obj.writeValueAsString(obj)));
	}

	@Test
	void deleteMethod() throws Exception {
		this.mockMvc.perform(delete("/pets/" + 1L)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	void putMethod() throws Exception {
		Long id = 3L;

		Pet obj = new Pet(id, "Lucy", LocalDate.of(2014, Month.JANUARY, 15), "German Shepherd", 'F');

		when(service.update(id, obj)).thenReturn(obj);

		this.mockMvc.perform(put("/pets/" + id).contentType("application/json").content(Obj.writeValueAsString(obj)))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().json(Obj.writeValueAsString(obj)));
	}
}
