package com.Leo.first_spring_crud.service;

import com.Leo.first_spring_crud.controller.Dto.CreateStockDto;
import com.Leo.first_spring_crud.entity.Stock;
import com.Leo.first_spring_crud.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        //DTO -> ENTITY
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
