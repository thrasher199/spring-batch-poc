package com.example.springbatchpoc.processor;

import com.example.springbatchpoc.domain.Coffee;
import com.example.springbatchpoc.domain.CoffeeStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CoffeeItemProcessor implements ItemProcessor<Coffee, CoffeeStock> {
    @Override
    public CoffeeStock process(Coffee item) throws Exception {
        log.info("Processing coffee with id:{}", item.getId());
        CoffeeStock coffeeStock = null;
        if(item.getId() != 100){
            coffeeStock = new CoffeeStock();
            coffeeStock.setCoffeId(item.getId());
            coffeeStock.setStockCount(item.getStockCount() + 2);
        }
        else
        {
            throw new RuntimeException("Error in processing");
        }

        return coffeeStock;
    }
}
