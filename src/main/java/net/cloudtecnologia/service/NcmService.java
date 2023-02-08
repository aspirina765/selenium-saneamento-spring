package net.cloudtecnologia.service;

import net.cloudtecnologia.model.entity.Ncm;

import java.util.Optional;


public interface NcmService {

    Optional<Ncm> findByNcm(String ncm);

    Ncm save(Ncm ncm);

}
