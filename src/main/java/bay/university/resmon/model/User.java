package bay.university.resmon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("user")
public class User{
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    @Field(name = "password")
    private String password;
    @Field(name = "name")
    private String name;
    @Field(name = "email")
    private String email;

    public User(){

    }

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
