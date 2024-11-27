package com.snzn.project.stock.repository;

import com.snzn.project.stock.repository.entity.Entry;
import com.snzn.project.stock.repository.entity.Quantity;
import com.snzn.project.stock.repository.entity.QuantityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    List<Quantity> findByEntryAndDeletedFalse(Entry entry);

    @Transactional
    @Modifying
    @Query("update Quantity q set q.status = :newStatus where q.orderNo = :orderNo and q.status = :currentStatus")
    int updateStatusByOrderNoAndStatus(QuantityStatus newStatus, String orderNo, QuantityStatus currentStatus);

    List<Quantity> findByOrderNoAndDeletedFalse(String orderNo);

    List<Quantity> findByStatusAndDeletedFalse(QuantityStatus quantityStatus);

}
