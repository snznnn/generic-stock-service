package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.OrderCreateRequest;
import com.snzn.project.stock.controller.model.OrderEntryQuantityModel;
import com.snzn.project.stock.controller.model.OrderUpdateRequest;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.QuantityRepository;
import com.snzn.project.stock.repository.entity.Quantity;
import com.snzn.project.stock.repository.entity.QuantityDirection;
import com.snzn.project.stock.repository.entity.QuantityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final QuantityRepository quantityRepository;
    private final EntryRepository entryRepository;

    public void create(OrderCreateRequest request) {
        List<Quantity> quantityList = new ArrayList<>();
        for (OrderEntryQuantityModel model : request.getEntryList()) {
            Quantity quantity = new Quantity(
                    entryRepository.getReferenceById(model.getEntryId()),
                    model.getQuantity(),
                    QuantityDirection.DOWN,
                    QuantityStatus.IN_PROGRESS,
                    request.getOrderNo()
            );
            quantityList.add(quantity);
        }
        quantityRepository.saveAll(quantityList);
    }

    public void complete(OrderUpdateRequest request) {
        quantityRepository.updateStatusByOrderNoAndStatus(QuantityStatus.COMPLETED, request.getOrderNo(), QuantityStatus.IN_PROGRESS);
    }

    public void cancel(OrderUpdateRequest request) {
        quantityRepository.updateStatusByOrderNoAndStatus(QuantityStatus.CANCELED, request.getOrderNo(), QuantityStatus.IN_PROGRESS);
    }

}
