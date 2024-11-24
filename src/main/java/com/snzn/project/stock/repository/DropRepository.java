package com.snzn.project.stock.repository;

import com.snzn.project.stock.repository.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DropRepository extends JpaRepository<Quantity, Long> {

}
