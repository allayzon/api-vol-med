package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.model.ConsultaModel;
import med.voll.api.model.MedicoModel;
import med.voll.api.model.PacienteModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve devolver null quando único médico cadastrado não estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0);

        var medico = cadastrarMedico("Dr. Fulano", "medico@vollmed.com", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Fulano", "paciente@gmail.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0);

        var medico = cadastrarMedico("Dr. Fulano", "medico@vollmed.com", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(MedicoModel medico, PacienteModel paciente, LocalDateTime data) {
        em.persist(new ConsultaModel(null, medico, paciente, data, null));
    }

    private MedicoModel cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new MedicoModel(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private PacienteModel cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new PacienteModel(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private Medico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new Medico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private Endereco dadosEndereco() {
        return new Endereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}