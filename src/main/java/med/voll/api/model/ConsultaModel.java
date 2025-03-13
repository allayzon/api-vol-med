package med.voll.api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import med.voll.api.domain.consulta.MotivoCancelamento;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@EqualsAndHashCode(of = "id")
public class ConsultaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoModel medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private PacienteModel paciente;

    private LocalDateTime data;

    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    public void cancelar(MotivoCancelamento motivo) {
        this.motivoCancelamento = motivo;
    }

    public ConsultaModel() {
    }

    public ConsultaModel(Long id, MedicoModel medico, PacienteModel paciente,  LocalDateTime data, MotivoCancelamento motivo) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public MedicoModel getMedico() {
        return medico;
    }

    public PacienteModel getPaciente() {
        return paciente;
    }

    public LocalDateTime getData() {
        return data;
    }
}
