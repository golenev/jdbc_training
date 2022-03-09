package utils;

public enum RowsName {
    ID("id"),

    NAME("name"),

    STATUS_ID("status_id"),

    METHOD_NAME("method_name"),

    PROJECT_ID("project_id"),

    SESSION_ID("project_id"),

    START_TIME("start_time"),

    END_TIME("end_time"),

    ENV("env"),

    BROWSER("browser"),

    AUTHOR_ID("author_id");

    String value;

    RowsName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


/*this.id = id;
        this.name = name;
        this.status_id = status_id;
        this.method_name = method_name;
        this.project_id = project_id;
        this.session_id = session_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.env = env;
        this.browser = browser;
        this.author_id = author_id;*/