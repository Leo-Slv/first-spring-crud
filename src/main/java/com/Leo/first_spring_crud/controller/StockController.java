package com.Leo.first_spring_crud.controller;

import com.Leo.first_spring_crud.controller.Dto.CreateStockDto;
import com.Leo.first_spring_crud.controller.Dto.CreateUserDto;
import com.Leo.first_spring_crud.entity.User;
import com.Leo.first_spring_crud.repository.StockRepository;
import com.Leo.first_spring_crud.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/stocks")

public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
}

    @PostMapping()
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto) {

        stockService.createStock(createStockDto);

        return ResponseEntity.ok().build();
    }

}
