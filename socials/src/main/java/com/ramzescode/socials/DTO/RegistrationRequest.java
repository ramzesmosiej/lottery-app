package com.ramzescode.socials.DTO;

import com.ramzescode.socials.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private String password;

    public User toUser() {
        return new User(name, surname, email, password);
    }
}
