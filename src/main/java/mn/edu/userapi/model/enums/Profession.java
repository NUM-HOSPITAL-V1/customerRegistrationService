package mn.edu.userapi.model.enums;

public enum Profession {
    TEACHER("Багш"),
    EMPLOYEE("Ажилтан"),
    STUDENT("Оюутан"),
    DOCTOR("Эмч"),
    NURSE("Сувилагч");

    private final String description;

    Profession(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}