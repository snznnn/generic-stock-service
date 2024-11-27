package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.OrderCreateRequest;
import com.snzn.project.stock.controller.model.OrderCreateResponse;
import com.snzn.project.stock.controller.model.OrderEntryPriceModel;
import com.snzn.project.stock.controller.model.OrderEntryQuantityModel;
import com.snzn.project.stock.controller.model.OrderUpdateRequest;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.QuantityRepository;
import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.repository.entity.Quantity;
import com.snzn.project.stock.repository.entity.QuantityDirection;
import com.snzn.project.stock.repository.entity.QuantityStatus;
import com.snzn.project.stock.service.exception.DuplicateRecordException;
import com.snzn.project.stock.service.exception.NotEnoughStockException;
import com.snzn.project.stock.service.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final QuantityService quantityService;
    private final QuantityRepository quantityRepository;
    private final EntryRepository entryRepository;

    public OrderCreateResponse create(OrderCreateRequest request) {
        List<OrderEntryPriceModel> entryPriceList = new ArrayList<>();
        List<Quantity> quantityList = new ArrayList<>();

        List<Quantity> quantityListByOrderNo = quantityRepository.findByOrderNoAndDeletedFalse(request.getOrderNo());
        if (!quantityListByOrderNo.isEmpty()) {
            throw new DuplicateRecordException();
        }

        for (OrderEntryQuantityModel entryQuantityModel : request.getEntryQuantityList()) {
            Optional<Entry> optEntry = entryRepository.findById(entryQuantityModel.getEntryId());
            if (optEntry.isEmpty()) {
                throw new RecordNotFoundException();
            }
            var entry = optEntry.get();

            Integer netQuantity = quantityService.getNetQuantity(entry);
            if (netQuantity < entryQuantityModel.getQuantity()) {
                throw new NotEnoughStockException();
            }

            var quantity = new Quantity(
                    entry,
                    entryQuantityModel.getQuantity(),
                    QuantityDirection.DOWN,
                    QuantityStatus.IN_PROGRESS,
                    request.getOrderNo()
            );
            quantityList.add(quantity);
            entryPriceList.add(new OrderEntryPriceModel(entry.getId(), entry.getPrice()));
        }

        quantityRepository.saveAll(quantityList);
        return new OrderCreateResponse(entryPriceList);
    }

    public void complete(OrderUpdateRequest request) {
        int updatedRowCount = quantityRepository.updateStatusByOrderNoAndStatus(QuantityStatus.COMPLETED, request.getOrderNo(), QuantityStatus.IN_PROGRESS);
        if (updatedRowCount == 0) {
            throw new RecordNotFoundException();
        }
    }

    public void cancel(OrderUpdateRequest request) {
        int updatedRowCount = quantityRepository.updateStatusByOrderNoAndStatus(QuantityStatus.CANCELED, request.getOrderNo(), QuantityStatus.IN_PROGRESS);
        if (updatedRowCount == 0) {
            throw new RecordNotFoundException();
        }
    }

}
