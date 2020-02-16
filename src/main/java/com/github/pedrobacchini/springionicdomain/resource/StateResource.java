package com.github.pedrobacchini.springionicdomain.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.springionicdomain.domain.City;
import com.github.pedrobacchini.springionicdomain.domain.State;
import com.github.pedrobacchini.springionicdomain.json.View;
import com.github.pedrobacchini.springionicdomain.service.CityService;
import com.github.pedrobacchini.springionicdomain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/state")
public class StateResource {

    private final StateService stateService;
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<State>> findAll() {
        List<State> states = stateService.findAll();
        return ResponseEntity.ok(states);
    }

    @JsonView(View.FindAll.class)
    @GetMapping("/{stateId}/city")
    public ResponseEntity<List<City>> findAllCitiesByState(@PathVariable Integer stateId) {
        List<City> cities = cityService.findAllByStateId(stateId);
        return ResponseEntity.ok(cities);
    }
}
