package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.QuantityChangeRequest;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.QuantityRepository;
import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.repository.entity.Quantity;
import com.snzn.project.stock.repository.entity.QuantityDirection;
import com.snzn.project.stock.repository.entity.QuantityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuantityService {

    private final QuantityRepository quantityRepository;
    private final EntryRepository entryRepository;

    public void add(QuantityChangeRequest request) {
        Entry entryReference = entryRepository.getReferenceById(request.getEntryId());

        Quantity quantity = new Quantity(
                entryReference,
                request.getQuantity(),
                QuantityDirection.UP,
                QuantityStatus.COMPLETED
        );

        quantityRepository.save(quantity);
    }

    // TODO make sure it wont go below zero with request
    public void remove(QuantityChangeRequest request) {
        Entry entryReference = entryRepository.getReferenceById(request.getEntryId());

        Quantity quantity = new Quantity(
                entryReference,
                request.getQuantity(),
                QuantityDirection.DOWN,
                QuantityStatus.COMPLETED
        );

        quantityRepository.save(quantity);
    }

    public Integer getNetQuantity(Entry entry) {
        List<Quantity> quantityList = quantityRepository.findByEntryAndDeletedFalse(entry);

        Integer netQuantity = 0;
        for (Quantity quantity : quantityList) {
            if (quantity.getDirection().equals(QuantityDirection.UP)) {
                netQuantity += quantity.getQuantity();
            } else {
                netQuantity -= quantity.getQuantity();
            }
        }

        return netQuantity;
    }

}
