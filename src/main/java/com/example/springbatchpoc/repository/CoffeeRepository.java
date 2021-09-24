package com.example.springbatchpoc.repository;

import com.example.springbatchpoc.domain.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    @Override
    Page<Coffee> findAll(Pageable pageable);
}
