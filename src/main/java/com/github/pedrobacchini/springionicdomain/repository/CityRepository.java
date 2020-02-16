package com.github.pedrobacchini.springionicdomain.repository;

import com.github.pedrobacchini.springionicdomain.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @SuppressWarnings("SpellCheckingInspection")
    @Transactional(readOnly = true)
    List<City> findAllByState_Id(Integer stateId);
}
