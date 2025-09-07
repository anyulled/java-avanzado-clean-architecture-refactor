package dev.arol.petclinic.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.arol.petclinic.application.port.in.CreateAppointmentUseCase;
import dev.arol.petclinic.application.port.in.GetAppointmentUseCase;
import dev.arol.petclinic.domain.model.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAppointmentUseCase createAppointmentUseCase;

    @MockBean
    private GetAppointmentUseCase getAppointmentUseCase;

    @Test
    @DisplayName("POST /appointments returns 201 with created appointment body")
    void createAppointment() throws Exception {
        Appointment request = new Appointment(1L, 2L, LocalDateTime.of(2025, 1, 1, 10, 0), "Checkup");
        Appointment created = new Appointment(10L, 2L, request.date(), request.reason());

        given(createAppointmentUseCase.createAppointment(any(Appointment.class))).willReturn(created);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.petId", is(2)))
                .andExpect(jsonPath("$.reason", is("Checkup")));
    }

    @Test
    @DisplayName("GET /appointments returns 200 with list of appointments")
    void getAllAppointments() throws Exception {
        List<Appointment> list = List.of(
                new Appointment(1L, 2L, LocalDateTime.of(2025, 1, 1, 10, 0), "A"),
                new Appointment(2L, 3L, LocalDateTime.of(2025, 1, 2, 11, 0), "B")
        );
        given(getAppointmentUseCase.getAllAppointments()).willReturn(list);

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].petId", is(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].petId", is(3)));
    }

    @Test
    @DisplayName("POST /appointments returns 400 with message when use case throws IllegalArgumentException")
    void createAppointment_badRequest() throws Exception {
        Appointment request = new Appointment(null, null, null, "");
        given(createAppointmentUseCase.createAppointment(any(Appointment.class))).willThrow(new IllegalArgumentException("Invalid appointment"));

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid appointment")));
    }
}