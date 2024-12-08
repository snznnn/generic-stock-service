package com.snzn.project.stock.controller.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryUpdateRequest {

    @NotNull
    private Long entryId;

    @Valid
    private List<PropertyNameValueModel> propertyList;

    private BigDecimal price;

}
