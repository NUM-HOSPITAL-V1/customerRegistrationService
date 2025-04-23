package mn.edu.userapi.model.enums;

public enum Gender {
    MALE("Эрэгтэй"),
    FEMALE("Эмэгтэй"),
    OTHER("Бусад");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}