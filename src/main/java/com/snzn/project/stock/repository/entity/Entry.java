package com.snzn.project.stock.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Entry extends BaseEntity {

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String definition;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 2000)
    private String concatenatedPropertyList;

    @DecimalMax(value = "1000000.0", inclusive = false)
    @DecimalMin(value = "1.0")
    @Column(nullable = false)
    private BigDecimal price;

}
