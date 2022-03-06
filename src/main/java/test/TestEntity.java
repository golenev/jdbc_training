package test;

public class TestEntity {
    private Integer id;
    private String name;

    public TestEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
