package utils;

public enum NumsAndIndexes {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    THREE_HUNDRED_FORTY_FOUR(344),
    THREE_HUNDRED_FORTY_FIVE(345);

    int value;

    NumsAndIndexes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
