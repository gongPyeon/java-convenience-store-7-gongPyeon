package store.domain;

public class User {
    private final boolean membership;

    public User(boolean membership) {
        this.membership = membership;
    }

    public boolean isMembership() {
        return membership;
    }

    public boolean checkMemberShip(String response) {
        if(response.equals("Y")){
            return true;
        }
        return  false;
    }
}
