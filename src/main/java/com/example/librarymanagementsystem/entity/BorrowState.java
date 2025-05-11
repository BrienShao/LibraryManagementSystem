package com.example.librarymanagementsystem.entity;

public enum BorrowState {
    UNCLAIMED("未领取"),
    CLAIMED("已领取"),
    UNREPAID("未归还"),
    REPAID("已归还");

    private final String description;

    BorrowState(String description) {
        this.description = description;
    }
}
