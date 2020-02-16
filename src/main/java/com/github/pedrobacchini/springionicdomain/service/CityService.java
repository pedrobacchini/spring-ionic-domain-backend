package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.City;
import com.github.pedrobacchini.springionicdomain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> findAllByStateId(Integer stateId) {
        return cityRepository.findAllByState_Id(stateId);
    }
}
