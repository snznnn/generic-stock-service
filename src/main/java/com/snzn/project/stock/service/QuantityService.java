package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.QuantityChangeRequest;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.QuantityRepository;
import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.repository.entity.Quantity;
import com.snzn.project.stock.repository.entity.QuantityDirection;
import com.snzn.project.stock.repository.entity.QuantityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuantityService {

    private final QuantityRepository quantityRepository;
    private final EntryRepository entryRepository;

    public void add(QuantityChangeRequest request) {
        Entry entryReference = entryRepository.getReferenceById(request.getEntryId());

        var quantity = new Quantity(
                entryReference,
                request.getQuantity(),
                QuantityDirection.UP,
                QuantityStatus.COMPLETED
        );

        quantityRepository.save(quantity);
    }

    public void remove(QuantityChangeRequest request) {
        Entry entryReference = entryRepository.getReferenceById(request.getEntryId());

        var quantity = new Quantity(
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
            if (!quantity.getStatus().equals(QuantityStatus.CANCELED)) {
                if (quantity.getDirection().equals(QuantityDirection.UP)) {
                    netQuantity += quantity.getQuantity();
                } else {
                    netQuantity -= quantity.getQuantity();
                }
            }
        }

        return netQuantity;
    }

    @Scheduled(fixedRate = 60000)
    private void cancelExpiredOrders() {
        List<Quantity> inProgressQuantityList = quantityRepository.findByStatusAndDeletedFalse(QuantityStatus.IN_PROGRESS);
        List<Quantity> expiredQuantityList = new ArrayList<>();
        for (Quantity quantity : inProgressQuantityList) {
            if (quantity.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                quantity.setStatus(QuantityStatus.CANCELED);
                expiredQuantityList.add(quantity);
            }
        }
        quantityRepository.saveAll(expiredQuantityList);
    }

}
