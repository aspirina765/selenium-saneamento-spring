package net.cloudtecnologia.model.repository;

import net.cloudtecnologia.model.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Integer> {
    Optional<Barcode> findByBarcode(String barcode);
}
