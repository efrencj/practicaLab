package com.tecnocampus.examsimulation.domain;

import com.tecnocampus.examsimulation.application.DTO.KingdomDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class Kingdom {
    private String id = UUID.randomUUID().toString();
    private int food;
    private int citizens;
    private int gold;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Kingdom() {
    }
    public Kingdom(KingdomDTO kingdomDTO) {
        this.id = kingdomDTO.id();
        this.food = kingdomDTO.food();
        this.citizens = kingdomDTO.citizens();
        this.gold = kingdomDTO.gold();
    }
    public String getId() {
        return id;
    }
    public int getFood() {
        return food;
    }
    public void setFood(int food) {
        this.food = food;
    }
    public int getCitizens() {
        return citizens;
    }
    public void setCitizens(int citizens) {
        this.citizens = citizens;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
