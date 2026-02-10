package entities;

public class Category {
    private final String name;

    public Category(String name) {
        this.name = name.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }
}
