package com.snzn.project.stock.controller;

import com.snzn.project.stock.controller.model.EntryDeleteRequest;
import com.snzn.project.stock.controller.model.EntryCreateRequest;
import com.snzn.project.stock.controller.model.EntryListResponse;
import com.snzn.project.stock.controller.model.EntryUpdateRequest;
import com.snzn.project.stock.service.EntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

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

    @PatchMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid EntryUpdateRequest request) {
        service.update(request);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody @Valid EntryDeleteRequest request) {
        service.softDelete(request.getId());
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<EntryListResponse> list(@RequestParam(required = false) String definition) {
        return new ResponseEntity<>(service.list(definition), OK);
    }

}
