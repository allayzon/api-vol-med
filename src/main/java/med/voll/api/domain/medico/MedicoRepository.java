package med.voll.api.domain.medico;

import med.voll.api.model.MedicoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
    Page<MedicoModel> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
            """)
    MedicoModel escolherMedicoAleatorioLivreNaData(@Param("especialidade") Especialidade especialidade, @Param("data") LocalDateTime data);

    @Query("""
            select m.ativo from Medico m
            where m.id = :id
            """)
    boolean findAtivoById(@Param("id")Long idMedico);
}
