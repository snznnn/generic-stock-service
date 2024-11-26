package com.snzn.project.stock.controller;

import com.snzn.project.stock.controller.model.QuantityChangeRequest;
import com.snzn.project.stock.service.QuantityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/quantity")
@RestController
public class QuantityController {

    private final QuantityService service;

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody @Valid QuantityChangeRequest request) {
        service.add(request);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody @Valid QuantityChangeRequest request) {
        service.remove(request);
        return new ResponseEntity<>(CREATED);
    }

}
