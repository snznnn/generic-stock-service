package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.EntryCreateRequest;
import com.snzn.project.stock.controller.model.EntryListResponse;
import com.snzn.project.stock.controller.model.EntryResponseModel;
import com.snzn.project.stock.controller.model.EntryUpdateRequest;
import com.snzn.project.stock.controller.model.PropertyNameValueModel;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.service.exception.DuplicateRecordException;
import com.snzn.project.stock.service.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class EntryService {

    private final EntryRepository repository;
    private final QuantityService quantityService;

    public void create(EntryCreateRequest request) {
        Optional<Entry> optEntry = repository.findByCategoryAndDefinitionAndBrandAndModelAndDeletedFalse(
                request.getCategory(),
                request.getDefinition(),
                request.getBrand(),
                request.getModel()
        );
        if (optEntry.isPresent()) {
            throw new DuplicateRecordException();
        }

        Entry entry = new Entry(
                request.getCategory(),
                request.getDefinition(),
                request.getBrand(),
                request.getModel(),
                concatenatePropertyList(request.getPropertyList())
        );
        repository.save(entry);
    }

    public void update(EntryUpdateRequest request) {
        Optional<Entry> optEntry = repository.findByIdAndDeletedFalse(request.getEntryId());
        if (optEntry.isEmpty()) {
            throw new RecordNotFoundException();
        } else {
            Entry entry = optEntry.get();
            entry.setConcatenatedPropertyList(concatenatePropertyList(request.getPropertyList()));
            repository.save(entry);
        }
    }

    private String concatenatePropertyList(List<PropertyNameValueModel> propertyList) {
        var concatenated = new StringBuilder("Product properties; ");
        for (int i = 0; i < propertyList.size(); i++) {
            PropertyNameValueModel model = propertyList.get(i);
            concatenated
                    .append(model.getName())
                    .append(": ")
                    .append(model.getValue());

            if (i + 1 < propertyList.size()) {
                concatenated.append(", ");
            }
        }
        return StringUtils.chop(concatenated.toString());
    }

    public void softDelete(Long id) {
        Optional<Entry> optEntry = repository.findById(id);
        if (optEntry.isPresent()) {
            Entry entry = optEntry.get();
            entry.softDelete();
            repository.save(entry);
        }
    }

    public EntryListResponse list(String definition) {
        List<Entry> entryList;

        if (isNull(definition)) {
            entryList = repository.findByDeletedFalse();
        } else {
            entryList = repository.findByDefinitionAndDeletedFalse(definition);
        }

        List<EntryResponseModel> entryModelList = new ArrayList<>();
        for (Entry entry : entryList) {
            Integer quantity = quantityService.getNetQuantity(entry);
            EntryResponseModel entryResponseModel = new EntryResponseModel(
                    entry.getId(),
                    quantity,
                    entry.getCategory(),
                    entry.getDefinition(),
                    entry.getBrand(),
                    entry.getModel(),
                    entry.getConcatenatedPropertyList()
            );
            entryModelList.add(entryResponseModel);
        }

        return new EntryListResponse(entryModelList);
    }

}
