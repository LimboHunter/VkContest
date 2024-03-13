package contest.vk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {

    private @Id
    @GeneratedValue Long id;
    private String username;
    private String password;
    private String name;
    private String role;

}
