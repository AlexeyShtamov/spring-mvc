package school.sorokin.springboot.springmvcexam.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import school.sorokin.springboot.springmvcexam.models.PetDto;
import school.sorokin.springboot.springmvcexam.models.UserDto;
import school.sorokin.springboot.springmvcexam.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {
        UserDto user = new UserDto(null, "Lesha", "lesha@mail.ru", 21);

        String userJson = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)).andExpect(status().isCreated())
                .andReturn()
                .getResponse().getContentAsString();

        UserDto createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(createdUser);
        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertEquals(user, createdUser);

    }

    @Test
    void shouldNotCreateNotValidUser() throws Exception {
        UserDto user = new UserDto(1L, "Lesha", "lesha@mail.ru", 21);

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)).andExpect(status().isBadRequest());
    }


    @Test
    void shouldSuccessGetUser() throws Exception {
        UserDto user = new UserDto(null, "Lesha", "lesha@mail.ru", 21);
        UserDto createdUser = userService.creteUser(user);

        String foundedUserJson = mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/{id}", createdUser.getId()))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse().getContentAsString();

        UserDto foundedUser = objectMapper.readValue(foundedUserJson, UserDto.class);

        Assertions.assertNotNull(foundedUser);
        Assertions.assertNotNull(foundedUser.getId());
        Assertions.assertEquals(user, foundedUser);
    }

    @Test
    void shouldNotSuccessGetPet() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSuccessUpdateUser() throws Exception {
        UserDto user = new UserDto(null, "Lesha", "lesha@mail.ru", 21);
        UserDto createdUser = userService.creteUser(user);

        UserDto newUser = new UserDto(null, "Leshka Shtamov", "lesha_shatomov@mail.ru", 22);
        String newUserJson = objectMapper.writeValueAsString(newUser);

        String updatedUserJson = mockMvc.perform(put("/users/{id}", createdUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        UserDto updatedUser = objectMapper.readValue(updatedUserJson, UserDto.class);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertNotNull(updatedUser.getId());
        Assertions.assertEquals(updatedUser.getId(), createdUser.getId());
        Assertions.assertNotEquals(updatedUser.getName(), user.getName());
        Assertions.assertNotEquals(updatedUser.getEmail(), user.getEmail());
        Assertions.assertNotEquals(updatedUser.getAge(), user.getAge());
    }

    @Test
    void shouldNotSuccessUpdateNonExistUser() throws Exception {
        UserDto user = new UserDto(null, "Lesha", "lesha@mail.ru", 21);
        userService.creteUser(user);

        UserDto newUser = new UserDto(null, "Leshka Shtamov", "lesha_shatomov@mail.ru", 22);
        String newUserJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(put("/users/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotSuccessUpdateNotValidUser() throws Exception {
        UserDto user = new UserDto(null, "Lesha", "lesha@mail.ru", 21);
        userService.creteUser(user);

        UserDto newUser = new UserDto(null, "Leshka Shtamov", "email", 22);
        String newUserJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessRemoveUser() throws Exception {
        UserDto userDto = new UserDto(null, "Lesha", "lesha@mail.com", 21);
        UserDto createdUser = userService.creteUser(userDto);


        mockMvc.perform(delete("/users/{id}", createdUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotSuccessRemoveUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}