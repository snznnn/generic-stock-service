package com.snzn.project.stock.controller.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @NotBlank
    private String orderNo;

    @Valid
    @NotEmpty
    private List<OrderEntryQuantityModel> entryList;

}
