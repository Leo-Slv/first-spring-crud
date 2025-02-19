package com.Leo.first_spring_crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_accounts_stocks")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;


    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;


    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;


    @Column(name = "quantify")
    private Integer quantify;


    public AccountStock() {
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantify) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.quantify = quantify;
    }

    public AccountStockId getId() {
        return id;
    }

    public void setId(AccountStockId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantify() {
        return quantify;
    }

    public void setQuantify(Integer quantify) {
        this.quantify = quantify;
    }
}