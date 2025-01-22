package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.AtualizarMedico;
import med.voll.api.domain.ListagemMedico;
import med.voll.api.domain.Medico;
import med.voll.api.domain.MedicoRepository;
import med.voll.api.model.MedicoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid Medico dadosMedico) {
        repository.save(new MedicoModel(dadosMedico));
    }

    @GetMapping
    public Page<ListagemMedico> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(ListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizarMedico(@RequestBody @Valid AtualizarMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirMedico(@PathVariable("id") Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

}
