package com.snzn.project.stock.controller;

import com.snzn.project.stock.controller.model.EntryCreateRequest;
import com.snzn.project.stock.controller.model.EntryListResponse;
import com.snzn.project.stock.service.EntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/entry")
@RestController
public class EntryController {

    private final EntryService service;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid EntryCreateRequest request) {
        service.create(request);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/list-all")
    public ResponseEntity<EntryListResponse> listAll() {
        return new ResponseEntity<>(service.listAll(), OK);
    }

}
