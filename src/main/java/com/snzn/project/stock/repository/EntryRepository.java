package com.snzn.project.stock.repository;

import com.snzn.project.stock.repository.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    Optional<Entry> findByCategoryAndDefinitionAndBrandAndModelAndDeletedFalse(String category, String definition, String brand, String model);

    List<Entry> findByDeletedFalse();

    List<Entry> findByDefinitionAndDeletedFalse(String definition);

    Optional<Entry> findByIdAndDeletedFalse(Long entryId);

}
