package store.domain;

import store.common.format.Format;

public class User {
    private final boolean membership;

    public User(boolean membership) {
        this.membership = membership;
    }

    public boolean isMembership() {
        return membership;
    }

    public boolean checkMemberShip(String response) {
        if(response.equals(Format.YES)){
            return true;
        }
        return  false;
    }
}
