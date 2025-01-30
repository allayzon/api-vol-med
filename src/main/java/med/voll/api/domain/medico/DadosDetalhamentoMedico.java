package med.voll.api.domain.medico;

import med.voll.api.model.EnderecoModel;
import med.voll.api.model.MedicoModel;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, EnderecoModel endereco) {

    public DadosDetalhamentoMedico(MedicoModel medicoModel) {
        this(
                medicoModel.getId(),
                medicoModel.getNome(),
                medicoModel.getEmail(),
                medicoModel.getCrm(),
                medicoModel.getTelefone(),
                medicoModel.getEspecialidade(),
                medicoModel.getEndereco());
    }
}
