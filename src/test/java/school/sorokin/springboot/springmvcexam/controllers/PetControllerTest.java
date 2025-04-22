package school.sorokin.springboot.springmvcexam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sorokin.springboot.springmvcexam.models.PetDto;
import school.sorokin.springboot.springmvcexam.models.UserDto;
import school.sorokin.springboot.springmvcexam.services.PetService;
import school.sorokin.springboot.springmvcexam.services.UserService;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldSuccessCreatePet() throws Exception {


        UserDto userDto = new UserDto(null, "Lesha", "lesha@mail.com", 21);
        userService.creteUser(userDto);
        PetDto petDto = new PetDto(null, "Pushok", 1L);

        String jsonPet = objectMapper.writeValueAsString(petDto);

        String createdPetJson = mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPet))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto createdPet = objectMapper.readValue(createdPetJson, PetDto.class);

        Assertions.assertNotNull(createdPet);
        Assertions.assertNotNull(createdPet.getId());
        Assertions.assertEquals(petDto, createdPet);

    }

    @Test
    void shouldNotCreateNoValidPet() throws Exception {

        UserDto userDto = new UserDto(null, "Lesha", "lesha@mail.com", 21);
        userService.creteUser(userDto);
        PetDto petDto = new PetDto(1L, "Pushok", 1L);

        String jsonPet = objectMapper.writeValueAsString(petDto);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPet))
                .andExpect(status().isBadRequest());


    }

    @Test
    void shouldNotCreatePetWithNonExistUser() throws Exception {

//        UserDto userDto = new UserDto(null, "Lesha", "lesha@mail.com", 21);
//        userService.creteUser(userDto);
        PetDto petDto = new PetDto(null, "Pushok", 1L);

        String jsonPet = objectMapper.writeValueAsString(petDto);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPet))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No user with id: 1", result.getResolvedException().getMessage()));

    }

    @Test
    void shouldSuccessGetPet() throws Exception {
        UserDto userDto = new UserDto(null, "Lesha", "lesha@mail.com", 21);
        userService.creteUser(userDto);
        PetDto petDto = new PetDto(1L, "Pushok", 1L);
        petService.createPet(petDto);

        String foundedPetJson = mockMvc.perform(get("/pets/{id}", 1L))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto foundedPet = objectMapper.readValue(foundedPetJson, PetDto.class);

        Assertions.assertNotNull(foundedPet);
        Assertions.assertNotNull(foundedPet.getId());
        Assertions.assertEquals(1L, foundedPet.getUserId());
        Assertions.assertEquals(petDto, foundedPet);

    }

    @Test
    void shouldNotSuccessGetPet() throws Exception {
        mockMvc.perform(get("/pets/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No pet with id: 1", result.getResolvedException().getMessage()));
    }

}