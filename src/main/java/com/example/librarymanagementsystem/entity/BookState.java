package com.example.librarymanagementsystem.entity;

public enum BookState {
    AVAILABLE("可借阅"),
    BORROWED("已借出"),
    RESERVED("已预约"),
    UNDER_MAINTENANCE("维护中"),
    LOST("遗失"),
    REMOVED("下架");

    private final String description;

    BookState(String description) {
        this.description = description;
    }

    // 校验状态转换是否合法
    public boolean canTransitionTo(BookState newState) {
        return switch (this) {
            case AVAILABLE ->
                    newState == BORROWED || newState == RESERVED || newState == UNDER_MAINTENANCE || newState == LOST;
            case BORROWED ->
                    newState == AVAILABLE || newState == RESERVED;
            case RESERVED ->
                    newState == BORROWED || newState == AVAILABLE;
            case UNDER_MAINTENANCE ->
                    newState == AVAILABLE || newState == LOST;
            case LOST ->
                    newState == AVAILABLE || newState == REMOVED;
            case REMOVED ->
                    false; // 终止状态不可变更
        };
    }
}
