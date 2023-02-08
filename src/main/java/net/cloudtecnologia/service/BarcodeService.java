package net.cloudtecnologia.service;

import net.cloudtecnologia.model.entity.Barcode;

import java.util.Optional;

public interface BarcodeService {
    Barcode save(Barcode barcode);
    Optional<Barcode> findByBarcode(String barcode);
}
