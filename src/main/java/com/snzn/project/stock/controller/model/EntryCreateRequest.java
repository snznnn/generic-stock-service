package com.snzn.project.stock.controller.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class EntryCreateRequest {

    @NotBlank
    private String category;

    @NotBlank
    private String definition;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Valid
    @NotEmpty
    private List<PropertyNameValueModel> propertyList;

    @DecimalMax(value = "1000000.0", inclusive = false)
    @DecimalMin(value = "1.0")
    @NotNull
    private BigDecimal price;

}
