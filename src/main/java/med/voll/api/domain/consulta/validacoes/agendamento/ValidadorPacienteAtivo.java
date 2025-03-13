package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(Consulta dados) {
        var paicenteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!paicenteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
