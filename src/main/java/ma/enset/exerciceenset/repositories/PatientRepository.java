package ma.enset.exerciceenset.repositories;

import ma.enset.exerciceenset.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByNom(String nom);
}
