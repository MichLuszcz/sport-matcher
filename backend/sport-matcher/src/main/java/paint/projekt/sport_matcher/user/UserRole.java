package paint.projekt.sport_matcher.user;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    public final String label;

    UserRole(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
}
}
