package med.voll.api.domain.paciente;

import med.voll.api.model.EnderecoModel;
import med.voll.api.model.PacienteModel;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String cpf, String telefone, EnderecoModel endereco) {

    public DadosDetalhamentoPaciente(PacienteModel pacienteModel) {
        this(pacienteModel.getId(), pacienteModel.getNome(), pacienteModel.getEmail(), pacienteModel.getCpf(), pacienteModel.getTelefone(), pacienteModel.getEndereco());
    }
}
