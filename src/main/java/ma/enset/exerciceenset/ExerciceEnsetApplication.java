package ma.enset.exerciceenset;

import ma.enset.exerciceenset.entities.*;
import ma.enset.exerciceenset.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ExerciceEnsetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExerciceEnsetApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            // Ajouter des produits
            productRepository.save(Product.builder().name("Computer").price(8500).quantity(12).build());
            productRepository.save(Product.builder().name("Printer").price(1200).quantity(34).build());
            productRepository.save(Product.builder().name("Smart Phone").price(3400).quantity(56).build());

            // Consulter tous les produits
            System.out.println("=== Tous les produits ===");
            List<Product> products = productRepository.findAll();
            products.forEach(p -> System.out.println(p));

            // Consulter un produit
            System.out.println("=== Consulter le produit avec id=1 ===");
            Product product = productRepository.findById(1L).orElse(null);
            System.out.println(product);

            // Chercher des produits
            System.out.println("=== Chercher les produits contenant 'C' ===");
            List<Product> searchResult = productRepository.findByNameContains("C");
            searchResult.forEach(p -> System.out.println(p));

            // Mettre à jour un produit
            System.out.println("=== Mettre a jour le produit id=1 ===");
            if (product != null) {
                product.setPrice(9000);
                product.setQuantity(20);
                productRepository.save(product);
                System.out.println(productRepository.findById(1L).orElse(null));
            }

            // Supprimer un produit
            System.out.println("=== Supprimer le produit id=2 ===");
            productRepository.deleteById(2L);
            System.out.println("=== Liste apres suppression ===");
            productRepository.findAll().forEach(p -> System.out.println(p));
        };
    }

    @Bean
    CommandLineRunner hospitalRunner(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            RendezVousRepository rendezVousRepository,
            ConsultationRepository consultationRepository) {
        return args -> {
            // === Ajouter des patients ===
            System.out.println("=== Ajout des patients ===");
            patientRepository.save(new Patient(null, "Mohamed", new Date(), false, 23, null));
            patientRepository.save(new Patient(null, "Hanane", new Date(), true, 56, null));
            patientRepository.save(new Patient(null, "Imane", new Date(), false, 78, null));

            // === Ajouter des medecins ===
            System.out.println("=== Ajout des medecins ===");
            medecinRepository.save(new Medecin(null, "Aymane", "aymane@gmail.com", "Cardio", null));
            medecinRepository.save(new Medecin(null, "Yasmine", "yasmine@gmail.com", "Dentiste", null));

            // === Ajouter un rendez-vous ===
            System.out.println("=== Ajout d'un rendez-vous ===");
            Patient patient = patientRepository.findByNom("Mohamed");
            Medecin medecin = medecinRepository.findByNom("Aymane");

            RendezVous rendezVous = new RendezVous();
            rendezVous.setId(UUID.randomUUID().toString());
            rendezVous.setDate(new Date());
            rendezVous.setStatus(StatusRDV.PENDING);
            rendezVous.setPatient(patient);
            rendezVous.setMedecin(medecin);
            RendezVous savedRDV = rendezVousRepository.save(rendezVous);

            // === Ajouter une consultation ===
            System.out.println("=== Ajout d'une consultation ===");
            RendezVous rdv = rendezVousRepository.findById(savedRDV.getId()).orElse(null);
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(new Date());
            consultation.setRapport("Rapport de la consultation...");
            consultation.setRendezVous(rdv);
            consultationRepository.save(consultation);

            // === Consulter tous les patients ===
            System.out.println("=== Tous les patients ===");
            patientRepository.findAll().forEach(p -> System.out.println(p.getNom()));

            // === Consulter tous les medecins ===
            System.out.println("=== Tous les medecins ===");
            medecinRepository.findAll().forEach(m -> System.out.println(m.getNom()));

            // === Consulter tous les rendez-vous ===
            System.out.println("=== Tous les rendez-vous ===");
            rendezVousRepository.findAll().forEach(r ->
                    System.out.println(r.getId() + " | " + r.getStatus() + " | patient=" + r.getPatient().getNom() + " | medecin=" + r.getMedecin().getNom()));

            // === Consulter toutes les consultations ===
            System.out.println("=== Toutes les consultations ===");
            consultationRepository.findAll().forEach(c ->
                    System.out.println("Consultation #" + c.getId() + " - " + c.getRapport()));
        };
    }
}
