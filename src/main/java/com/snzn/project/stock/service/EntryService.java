package com.snzn.project.stock.service;

import com.snzn.project.stock.controller.model.EntryCreateRequest;
import com.snzn.project.stock.controller.model.EntryListResponse;
import com.snzn.project.stock.controller.model.EntryResponseModel;
import com.snzn.project.stock.controller.model.PropertyNameValueModel;
import com.snzn.project.stock.repository.EntryRepository;
import com.snzn.project.stock.repository.entity.Entry;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public void create(EntryCreateRequest request) {
        Entry entry = new Entry(
                request.getCategory(),
                request.getDefinition(),
                request.getBrand(),
                request.getModel(),
                concatenatePropertyList(request.getPropertyList())
        );
        entryRepository.save(entry);
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

    public EntryListResponse listAll() {
        return new EntryListResponse(
                entryRepository.findAll()
                        .stream()
                        .map(entry ->
                                new EntryResponseModel(
                                        entry.getId(),
                                        0, // TODO get from quantity
                                        entry.getCategory(),
                                        entry.getDefinition(),
                                        entry.getBrand(),
                                        entry.getModel(),
                                        entry.getConcatenatedPropertyList()
                                )
                        )
                        .collect(Collectors.toList())
        );
    }

}
