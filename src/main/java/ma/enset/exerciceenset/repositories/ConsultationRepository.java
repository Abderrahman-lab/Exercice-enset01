package ma.enset.exerciceenset.repositories;

import ma.enset.exerciceenset.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
