package ms.studies.bookmicrosservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.studies.bookmicrosservice.dto.BookRequestDto;
import ms.studies.bookmicrosservice.dto.BookResponseDto;
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

import java.util.UUID;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService service;

    /** Tests of the v1/book/register route **/

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
    void validHandleValidationErrorsBookWithoutName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = "{\"number\": 1}";
        ApiErrors apiErrors = new ApiErrors("O campo 'name' é obrigatório.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorsBookNameBlank() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequestDto request = new BookRequestDto("      ", 1);
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

    @Test
    void validHandleValidationErrorsBookWithoutNumber() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = "{\"name\": \"Teste\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'number' é obrigatório.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorsBookNumberWrongType() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = "{\"number\": \"Teste\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'number' aceita somente dados do tipo Integer.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** Tests of the v1/book/consult/{id} route **/

    @Test
    void validHandleValidationBookNumberPathparamWrongType() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrors apiErrors = new ApiErrors("O parametro 'bookNumber' aceita somente dados do tipo Integer.");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/book/consult/ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationBookNumberPathparamblank() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrors apiErrors = new ApiErrors("O parametro 'bookNumber' é obrigatório na rota.");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/book/consult/   ")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** Tests of the v1/book/delete/{id} route **/

    @Test
    void validHandleValidationIdPathparamWrongType() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' aceita somente dados do tipo UUID.");

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/book/delete/ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationIdPathparamblank() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' é obrigatório na rota.");

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/book/delete/   ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

}
