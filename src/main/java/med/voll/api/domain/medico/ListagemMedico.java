package med.voll.api.domain.medico;

import med.voll.api.model.MedicoModel;

public record ListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public ListagemMedico(MedicoModel medicoModel) {
        this(medicoModel.getId(), medicoModel.getNome(), medicoModel.getEmail(), medicoModel.getCrm(), medicoModel.getEspecialidade());
    }
}
