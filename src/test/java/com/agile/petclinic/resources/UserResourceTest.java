package com.agile.petclinic.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agile.petclinic.entities.User;
import com.agile.petclinic.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserResource.class)
class UserResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService service;

	@Autowired
	ObjectMapper Obj;

	static User usr1;
	static User usr2;

	@BeforeAll
	static void initAll() {
		usr1 = new User(null, "admin", "admin", "*****");
		usr2 = new User(null, "user", "user", "*****");
	}

	@Test
	void getAllMethod() throws Exception {
		List<User> list = Arrays.asList(usr1, usr2);

		when(service.findAll()).thenReturn(list);

		this.mockMvc.perform(get("/pets")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(list)));
	}
	
	@Test
	void getOneMethod() throws Exception {
		Long id = usr2.getId();
		
		when(service.findById(id)).thenReturn(usr2);

		this.mockMvc.perform(get("/pets/" + id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(usr2)));
	}
	
	@Test
	void postMethod() throws Exception {
		User obj = new User(3L, "Jack", "jack", "*****");

		when(service.insert(obj)).thenReturn(obj);
		
		this.mockMvc.perform(post("/pets").contentType("application/json").content(Obj.writeValueAsString(obj))).andDo(print()).andExpect(status().isCreated())
		.andExpect(content().json(Obj.writeValueAsString(obj)));
	}

	@Test
	void deleteMethod() throws Exception {		
		this.mockMvc.perform(delete("/pets/" + 2L)).andDo(print()).andExpect(status().isNoContent());
	}
	
	@Test
	void putMethod() throws Exception {
		Long id = 2L;
		
		User obj = new User(id, "Jack", "jack", "*****");

		when(service.update(id, obj)).thenReturn(obj);
		
		this.mockMvc.perform(put("/pets/" + id).contentType("application/json").content(Obj.writeValueAsString(obj))).andDo(print()).andExpect(status().isOk())
		.andExpect(content().json(Obj.writeValueAsString(obj)));
	}
	
	@Test
	void putMethodWithoutPassword() throws Exception {
		Long id = 2L;
		
		User obj = new User(id, "Jack", "jack", null);
		
		when(service.update(id, obj)).thenReturn(obj);
		
		this.mockMvc.perform(put("/pets/" + id).contentType("application/json").content(Obj.writeValueAsString(obj))).andDo(print()).andExpect(status().isOk())
		.andExpect(content().json(Obj.writeValueAsString(obj)));
	}
}
