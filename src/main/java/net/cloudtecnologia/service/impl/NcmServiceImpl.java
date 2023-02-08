package net.cloudtecnologia.service.impl;

import net.cloudtecnologia.model.entity.Ncm;
import net.cloudtecnologia.model.repository.NcmRepository;
import net.cloudtecnologia.service.NcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NcmServiceImpl implements NcmService {

    @Autowired
    private NcmRepository repository;

    @Override
    public Optional<Ncm> findByNcm(String ncm) {
        return repository.findByNcm(ncm);
    }

    @Override
    public Ncm save(Ncm ncm) {
        return repository.save(ncm);
    }
}
