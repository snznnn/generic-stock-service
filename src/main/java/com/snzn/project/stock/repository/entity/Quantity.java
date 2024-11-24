package com.snzn.project.stock.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quantity extends BaseEntity {

    @Column(nullable = false)
    private Integer quantity;

    private QuantityDirection direction;

    @ManyToOne
    @JoinColumn
    private Entry entry;

}
