package Model.Entity;

public class User extends Entity {
    private String UserId;

    public User(String id) {
        super(id);
        this.UserId = id;
    }

    public void reactTo(Entity entity) {
        entity.decreaseEnergy(30);
    }

    public String getUserId() {
        return UserId;
    }
}
