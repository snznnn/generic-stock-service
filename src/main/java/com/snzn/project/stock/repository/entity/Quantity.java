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

    @ManyToOne
    @JoinColumn
    private Entry entry;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private QuantityDirection direction;

    @Column(nullable = false)
    private QuantityStatus status;

    private String orderNo;

    public Quantity(Entry entry, Integer quantity, QuantityDirection direction, QuantityStatus status) {
        this.entry = entry;
        this.quantity = quantity;
        this.direction = direction;
        this.status = status;
    }

}
