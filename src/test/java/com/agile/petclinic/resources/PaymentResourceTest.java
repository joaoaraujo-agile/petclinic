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

import com.agile.petclinic.dto.PaymentRequestDTO;
import com.agile.petclinic.entities.Appointment;
import com.agile.petclinic.entities.Payment;
import com.agile.petclinic.entities.PaymentHistory;
import com.agile.petclinic.entities.enums.AppointmentType;
import com.agile.petclinic.entities.enums.PaymentType;
import com.agile.petclinic.services.AppointmentService;
import com.agile.petclinic.services.PaymentHistoryService;
import com.agile.petclinic.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PaymentResource.class)
class PaymentResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PaymentService service;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private PaymentHistoryService paymentHistoryService;

	@Autowired
	ObjectMapper Obj;

	static Payment pay1;
	static Payment pay2;

	static Payment pay3;

	static Appointment ap1;
	static Appointment ap2;
	static Appointment ap3;

	static PaymentHistory ph1;
	static PaymentHistory ph2;
	static PaymentHistory ph3;

	@BeforeAll
	static void initAll() {
		ap1 = new Appointment(1L, Instant.parse("2021-05-13T12:00:00Z"), "Routine exam", AppointmentType.MEDICAL, null,
				null, null);
		ap2 = new Appointment(2L, Instant.parse("2021-05-14T11:00:00Z"), "Surgery", AppointmentType.MEDICAL, null, null,
				null);
		ap3 = new Appointment(3L, Instant.parse("2021-05-13T12:00:00Z"), "Shower and Grooming",
				AppointmentType.GROOMING, null, null, null);

		pay1 = new Payment(2L, null, null, null, ap2);
		ap2.setPayment(pay1);
		pay2 = new Payment(3L, null, null, null, ap3);
		ap3.setPayment(pay2);

		pay3 = new Payment(1L, Instant.now(), 250.00, PaymentType.CASH, ap1);

		ph1 = new PaymentHistory(2L, pay1);
		pay1.setHistory(ph1);
		ph2 = new PaymentHistory(3L, pay2);
		pay2.setHistory(ph2);
		ph3 = new PaymentHistory(1L, pay3);
		pay3.setHistory(ph3);
	}

	@Test
	void getAllMethod() throws Exception {
		List<Payment> list = Arrays.asList(pay1, pay2);

		when(service.findAll()).thenReturn(list);

		this.mockMvc.perform(get("/payments")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(list)));
	}

	@Test
	void getOneMethod() throws Exception {
		Long id = pay1.getId();

		when(service.findById(id)).thenReturn(pay1);

		this.mockMvc.perform(get("/payments/" + id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(pay1)));
	}

	@Test
	void postMethod() throws Exception {
		PaymentRequestDTO objDTO = PaymentRequestDTO.toDTO(pay3);

		when(service.insert(objDTO)).thenReturn(pay3);

		this.mockMvc.perform(post("/payments").contentType("application/json").content(Obj.writeValueAsString(objDTO)))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().json(Obj.writeValueAsString(pay3)));
	}

	@Test
	void deleteMethod() throws Exception {
		this.mockMvc.perform(delete("/payments/" + 3L)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	void putMethod() throws Exception {
		when(service.update(pay3.getId(), pay3)).thenReturn(pay3);

		this.mockMvc
				.perform(put("/payments/" + pay3.getId()).contentType("application/json")
						.content(Obj.writeValueAsString(pay3)))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().json(Obj.writeValueAsString(pay3)));
	}

	@Test
	void getPaymentHistory() throws Exception {
		List<PaymentHistory> list = Arrays.asList(ph1, ph2, ph3);
		when(paymentHistoryService.findAll()).thenReturn(list);

		this.mockMvc.perform(get("/payments/history")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(Obj.writeValueAsString(list)));
	}
}
