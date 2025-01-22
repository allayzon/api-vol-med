package med.voll.api.domain;

import jakarta.validation.constraints.NotNull;

public record AtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        Endereco endereco) {
}
