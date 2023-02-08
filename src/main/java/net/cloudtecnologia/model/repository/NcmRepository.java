package net.cloudtecnologia.model.repository;

import net.cloudtecnologia.model.entity.Ncm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NcmRepository extends JpaRepository<Ncm, Integer> {
    Optional<Ncm> findByNcm(String ncm);
}
