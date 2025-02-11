package med.voll.api.domain.paciente;

import med.voll.api.model.EnderecoModel;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String cpf, String telefone, EnderecoModel endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
