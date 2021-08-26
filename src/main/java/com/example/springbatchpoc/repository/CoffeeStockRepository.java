package com.example.springbatchpoc.repository;

import com.example.springbatchpoc.domain.CoffeeStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeStockRepository extends JpaRepository<CoffeeStock, Long> {
}
