package Controller.Load;

import Controller.FileUtils.JsonIO;
import Model.Entity.Entity;
import Model.Entity.User;

import java.io.File;

public class LoadEntity {
    public LoadEntity() {

    }

    public Entity buildEntity(File file) {
        Entity entity = new User("test");
        System.out.println("Loading entity file: " + file.getName());
        return entity;
    }
}
