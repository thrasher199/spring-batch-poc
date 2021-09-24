package com.example.springbatchpoc.repository;

import com.example.springbatchpoc.domain.CoffeeStock;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CoffeeStockRepositoryTest {

    @Autowired
    private CoffeeStockRepository coffeeStockRepository;

    @Test
    public void find_all_repo(){
        List<CoffeeStock> coffeeStockList = coffeeStockRepository.findAll();
        assertTrue(coffeeStockList.size() > 0);

    }

}