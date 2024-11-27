package com.snzn.project.stock.controller;

import com.snzn.project.stock.controller.model.OrderCreateRequest;
import com.snzn.project.stock.controller.model.OrderUpdateRequest;
import com.snzn.project.stock.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid OrderCreateRequest request) {
        service.create(request);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody @Valid OrderUpdateRequest request) {
        service.complete(request);
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestBody @Valid OrderUpdateRequest request) {
        service.cancel(request);
        return new ResponseEntity<>(CREATED);
    }

}
