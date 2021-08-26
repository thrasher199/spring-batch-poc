package com.example.springbatchpoc.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "coffee_stock")
public class CoffeeStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long coffeId;
    private int stockCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoffeId() {
        return coffeId;
    }

    public void setCoffeId(long coffeId) {
        this.coffeId = coffeId;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    @Override
    public String toString() {
        return "CoffeeStock{" +
                "id=" + id +
                ", coffeId=" + coffeId +
                ", stockCount=" + stockCount +
                '}';
    }
}
