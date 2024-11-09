package store.domain;

public class User {
    private final boolean membership;

    public User(boolean membership) {
        this.membership = membership;
    }

    public boolean isMembership() {
        return membership;
    }
}
