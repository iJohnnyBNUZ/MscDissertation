package Model.Entity;

public class User extends Entity {
    private String UserId;
    private Boolean isOnline;

    public User(String id) {
        super(id);
        this.UserId = id;
        this.isOnline = true;
    }

    public void reactTo(Entity entity) {
        entity.decreaseEnergy(30);
    }

    public String getUserId() {
        return UserId;
    }

    public void logout(){
        isOnline = false;
    }
}
