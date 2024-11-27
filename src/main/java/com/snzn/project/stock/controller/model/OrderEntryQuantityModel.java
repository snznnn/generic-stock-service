package com.snzn.project.stock.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntryQuantityModel {

    @NotNull
    private Long entryId;

    @NotNull
    private Integer quantity;

}
