package com.ms.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.controllers.UserController;
import com.ms.user.dtos.UserDto;
import com.ms.user.models.User;
import com.ms.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void deveAdicionarUsuarioERetornar201() throws Exception {
        UserDto dto = new UserDto("Nicolas", "nicolas@email.com");
        User userSalvo = new User("Nicolas", "nicolas@email.com", "PENDENTE");
        userSalvo.setId(1L);

        when(userService.create(any(UserDto.class))).thenReturn(userSalvo);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nicolas"))
                .andExpect(jsonPath("$.email").value("nicolas@email.com"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveRetornar400QuandoEmailForVazio() throws Exception {
        UserDto dto = new UserDto("Nicolas", ""); // email vazio

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar500QuandoServiceLancarExcecao() throws Exception {
        UserDto dto = new UserDto("Nicolas", "nicolas@email.com");

        when(userService.create(any(UserDto.class)))
                .thenThrow(new RuntimeException("Erro inesperado no banco"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }
}
