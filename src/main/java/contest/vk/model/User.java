package contest.vk.model;

import lombok.Data;

@Data

public class User {
    Long id;

    String name;

    String username;

    String email;

    Address address;

    String phone;

    String website;

    Company company;
}