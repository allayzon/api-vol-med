package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.Endereco;

public record AtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        Endereco endereco) {
}
