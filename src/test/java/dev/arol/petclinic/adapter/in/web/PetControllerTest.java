package dev.arol.petclinic.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.arol.petclinic.application.port.in.CreatePetUseCase;
import dev.arol.petclinic.application.port.in.GetPetUseCase;
import dev.arol.petclinic.domain.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreatePetUseCase createPetUseCase;

    @MockBean
    private GetPetUseCase getPetUseCase;

    @Test
    @DisplayName("POST /pets returns 201 with created pet body")
    void createPet_success() throws Exception {
        Pet request = new Pet(null, "Fluffy", "Cat", "Alice");
        Pet created = new Pet(5L, request.name(), request.species(), request.ownerName());

        given(createPetUseCase.createPet(any(Pet.class))).willReturn(created);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("Fluffy")))
                .andExpect(jsonPath("$.species", is("Cat")))
                .andExpect(jsonPath("$.ownerName", is("Alice")));
    }

    @Test
    @DisplayName("GET /pets returns 200 with list of pets")
    void getAllPets_success() throws Exception {
        List<Pet> list = List.of(
                new Pet(1L, "Bella", "Dog", "Bob"),
                new Pet(2L, "Milo", "Cat", "Carol")
        );
        given(getPetUseCase.getAllPets()).willReturn(list);

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Bella")))
                .andExpect(jsonPath("$[0].species", is("Dog")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Milo")))
                .andExpect(jsonPath("$[1].species", is("Cat")));
    }
}
