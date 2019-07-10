package Model.Entity;

public class User extends Entity {
    String UserId;

    public User(String id){
        super(id);
        this.UserId = id;
    }
}
