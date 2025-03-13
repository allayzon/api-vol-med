package med.voll.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    private MockMvc mvc;

    @Autowired
    private JacksonTester<Consulta> jsonConsultaEnvio;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jsonConsultaRetorno;

    @Mock
    private AgendaDeConsultas agenda; // Mock da dependência

    @InjectMocks
    private ConsultaController consultaController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(consultaController).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    @DisplayName("Deve devolver codigo http 400 quando as informacoes forem invalidas")
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver codigo http 200 quando as informacoes forem validas")
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dados = new DadosDetalhamentoConsulta(null, 21L, 51L, data);

        when(agenda.agendar(any())).thenReturn(dados);

        var response = mvc
                .perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConsultaEnvio.write(
                                        new Consulta(21L, 51L, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        var jsonEsperado = jsonConsultaRetorno.write(
                dados
        ).getJson();

        assertEquals(jsonEsperado, response.getContentAsString());
    }

}