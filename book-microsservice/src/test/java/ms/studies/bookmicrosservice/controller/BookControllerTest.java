package ms.studies.bookmicrosservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.exception.ApiErrors;
import ms.studies.bookmicrosservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService service;

    @Test
    void validHandleValidationErrorsBookNameNull() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequestDto request = new BookRequestDto(null, 1);
        ApiErrors apiErrors = new ApiErrors("O campo 'name' é obrigatório.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorsBookNumberNull() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequestDto request = new BookRequestDto("teste", null);
        ApiErrors apiErrors = new ApiErrors("O campo 'number' é obrigatório.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }
}
