package com.snzn.project.stock.controller.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryUpdateRequest {

    @NotNull
    private Long entryId;

    @Valid
    @NotEmpty
    private List<PropertyNameValueModel> propertyList;

}
