package uws.ah.bustrackergeo;

public class User {

    public String fullName, email;
    public Boolean elevatedPriv;

    public User(){

    }

    public User(String fullName, String email, Boolean elevatedPriv) {
        this.fullName = fullName;
        this.email = email;
        this.elevatedPriv = elevatedPriv;
    }
}
