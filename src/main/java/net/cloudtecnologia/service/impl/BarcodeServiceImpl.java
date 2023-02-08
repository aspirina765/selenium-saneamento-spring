package net.cloudtecnologia.service.impl;

import net.cloudtecnologia.model.entity.Barcode;
import net.cloudtecnologia.model.repository.BarcodeRepository;
import net.cloudtecnologia.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    @Autowired
    private BarcodeRepository repository;

    @Override
    public Barcode save(Barcode barcode) {
        return repository.save(barcode);
    }

    @Override
    public Optional<Barcode> findByBarcode(String barcode) {
        return repository.findByBarcode(barcode);
    }
}
