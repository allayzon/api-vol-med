package med.voll.api.domain.paciente;

import med.voll.api.model.PacienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    Page<PacienteModel> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select p.ativo from Paciente p
            where p.id = :id
            """)
    boolean findAtivoById(@Param("id")Long idPaciente);
}
