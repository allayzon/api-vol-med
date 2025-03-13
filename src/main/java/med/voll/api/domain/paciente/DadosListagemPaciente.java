package med.voll.api.domain.paciente;

import med.voll.api.model.PacienteModel;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

    public DadosListagemPaciente(PacienteModel pacienteModel) {
        this(pacienteModel.getId(), pacienteModel.getNome(), pacienteModel.getEmail(), pacienteModel.getCpf());
    }

}
