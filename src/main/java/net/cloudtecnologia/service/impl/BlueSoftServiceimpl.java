package net.cloudtecnologia.service.impl;

import net.cloudtecnologia.model.entity.BlueSoft;
import net.cloudtecnologia.model.repository.BlueSoftRepository;
import net.cloudtecnologia.service.BlueSoftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlueSoftServiceimpl implements BlueSoftService {

    @Autowired
    private BlueSoftRepository repository;

    @Override
    @Transactional
    public void saveAll(List<BlueSoft> listaObj) {
        repository.saveAll(listaObj);
    }


}
