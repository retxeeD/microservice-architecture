package ms.studies.bookmicrosservice.controller;

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

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService service;

    ObjectMapper objectMapper = new ObjectMapper();

    /** Tests of the v1/book/register route **/

    @Test
    void validHandleValidationErrorsBookNameNull() throws Exception {
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
        String request = "{\"number\": \"Teste\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'number' aceita somente dados do tipo Integer.");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validInvlaidContentTypeRegisterRoute() throws Exception{
        ApiErrors apiErrors = new ApiErrors("O header 'Content-Type' recebe apenas o valor  'application/json'");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book/register")
                        .contentType(MediaType.TEXT_XML))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** Tests of the v1/book/consult/{id} route **/

    @Test
    void validHandleValidationBookNumberPathparamWrongType() throws Exception {
        ApiErrors apiErrors = new ApiErrors("O parametro 'bookNumber' aceita somente dados do tipo Integer.");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/book/consult/ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationBookNumberPathparamblank() throws Exception{
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
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' aceita somente dados do tipo UUID.");

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/book/delete/ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationIdPathparamblank() throws Exception{
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' é obrigatório na rota.");

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/book/delete/   ")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

}
