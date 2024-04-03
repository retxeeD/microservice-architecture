package ms.studies.personmicrosservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.studies.personmicrosservice.dto.PersonRequestDto;
import ms.studies.personmicrosservice.dto.RentBookRequestDto;
import ms.studies.personmicrosservice.exception.ApiErrors;
import ms.studies.personmicrosservice.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService service;

    ObjectMapper objectMapper = new ObjectMapper();

    /** /register **/

    @Test
    void validHandleValidationErrorRegisterDocumentInvalidValue() throws Exception {
        PersonRequestDto request = new PersonRequestDto("ABC", "Teste teste");
        ApiErrors apiErrors = new ApiErrors("O campo 'document' deve ser informado com valor válido.");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterDocumentNullValue() throws Exception {
        PersonRequestDto request = new PersonRequestDto(null, "Teste teste");
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterWithoutDocument() throws Exception {
        String request = "{\"name\": \"Teste teste\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterDocumentBlankValue() throws Exception {
        String request = "{\"document\": \"     \",\"name\": \"Teste teste\"}";
        List<String> messages = Arrays.asList("O campo 'document' deve ser informado com valor válido.","O campo 'document' é obrigatório.");
        ApiErrors apiErrors = new ApiErrors(messages);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterNameBlankValue() throws Exception {
        String request = "{\"document\": \"42166595863\",\"name\": \"     \"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'name' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterNameNullValue() throws Exception {
        String request = "{\"document\": \"42166595863\",\"name\": null}";
        ApiErrors apiErrors = new ApiErrors("O campo 'name' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRegisterWithoutName() throws Exception {
        String request = "{\"document\": \"42166595863\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'name' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/person/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** /consult/{personDocument} **/

    @Test
    void validHandleValidationErrorPathParamPersonDocumentInvalidValue() throws Exception {
        ApiErrors apiErrors = new ApiErrors("Informe documento válido.");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/person/consult/ABC"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorPathParamPersonDocumentBlankValue() throws Exception {
        ApiErrors apiErrors = new ApiErrors("Informe documento válido.");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/person/consult/     "))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** /delete/{id} **/

    @Test
    void validHandleValidationErrorPathParamIdInvalidValue() throws Exception {
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' aceita somente dados do tipo UUID.");
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/person/delete/ABC"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorPathParamIdBlankValue() throws Exception {
        ApiErrors apiErrors = new ApiErrors("O parametro 'id' é obrigatório na rota.");
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/person/delete/      "))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** /rent-book **/

    @Test
    void validHandleValidationErrorRentBookDocumentInvalidValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto("ABC", 1);
        ApiErrors apiErrors = new ApiErrors("O campo 'document' deve ser informado com valor válido.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookDocumentNullValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto(null, 1);
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookWithoutDocument() throws Exception {
        String request = "{\"rentBook\": 1}";
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookDocumentBlankValue() throws Exception {
        String request = "{\"document\": \"      \", \"rentBook\": 1}";
        List<String> messages = Arrays.asList("O campo 'document' deve ser informado com valor válido.","O campo 'document' é obrigatório.");
        ApiErrors apiErrors = new ApiErrors(messages);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookRentBookInvalidValue() throws Exception {
        String request = "{\"document\": \"42166595863\", \"rentBook\": \"ABC\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'rentBook' aceita somente dados do tipo Integer.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookRentBookNullValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto("422166595863", null);
        List<String> messages = Arrays.asList("O campo 'document' deve ser informado com valor válido.", "O campo 'rentBook' é obrigatório.");
        ApiErrors apiErrors = new ApiErrors(messages);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorRentBookWithoutRentBook() throws Exception {
        String request = "{\"document\": \"42166595863\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'rentBook' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/rent-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    /** /return-book **/

    @Test
    void validHandleValidationErrorReturnBookDocumentInvalidValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto("ABC", 1);
        ApiErrors apiErrors = new ApiErrors("O campo 'document' deve ser informado com valor válido.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookDocumentNullValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto(null, 1);
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookWithoutDocument() throws Exception {
        String request = "{\"rentBook\": 1}";
        ApiErrors apiErrors = new ApiErrors("O campo 'document' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookDocumentBlankValue() throws Exception {
        String request = "{\"document\": \"      \", \"rentBook\": 1}";
        List<String> messages = Arrays.asList("O campo 'document' deve ser informado com valor válido.","O campo 'document' é obrigatório.");
        ApiErrors apiErrors = new ApiErrors(messages);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookRentBookInvalidValue() throws Exception {
        String request = "{\"document\": \"42166595863\", \"rentBook\": \"ABC\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'rentBook' aceita somente dados do tipo Integer.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookRentBookNullValue() throws Exception {
        RentBookRequestDto request = new RentBookRequestDto("422166595863", null);
        List<String> messages = Arrays.asList("O campo 'document' deve ser informado com valor válido.", "O campo 'rentBook' é obrigatório.");
        ApiErrors apiErrors = new ApiErrors(messages);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

    @Test
    void validHandleValidationErrorReturnBookWithoutRentBook() throws Exception {
        String request = "{\"document\": \"42166595863\"}";
        ApiErrors apiErrors = new ApiErrors("O campo 'rentBook' é obrigatório.");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/person/return-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(apiErrors)));
    }

}
