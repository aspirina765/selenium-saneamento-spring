package net.cloudtecnologia.model.repository;

import net.cloudtecnologia.model.entity.BlueSoft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlueSoftRepository extends JpaRepository<BlueSoft, Integer> {
    Optional<BlueSoft> findByBarcode(String barcode);
}
