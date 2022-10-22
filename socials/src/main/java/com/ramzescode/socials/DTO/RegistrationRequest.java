package com.ramzescode.socials.DTO;

import com.ramzescode.socials.domain.AppUser;
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

    public AppUser toUser() {
        return new AppUser(name, surname, email, password);
    }
}
