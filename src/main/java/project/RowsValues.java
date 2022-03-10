package project;

public enum RowsValues {
    DONALD_TRUMP("Donald Trump"),
    MOST_SMART_METHOD("mostSmartMethod"),
    JDBC_CONNECTOR("JDBC Connection");

    String value;

    RowsValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
