package com.snzn.project.stock.repository;

import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.repository.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    List<Quantity> findByEntryAndDeletedFalse(Entry entry);

}
