package com.Leo.first_spring_crud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name ="tb_stocks")
public class Stock {


    @Column(name = "stock_id")
    @Id
    private String stockId;


    @Column(name = "description")
    private String description;


    public Stock() {
    }

    public Stock(String stockId, String description) {
        this.stockId = stockId;
        this.description = description;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
