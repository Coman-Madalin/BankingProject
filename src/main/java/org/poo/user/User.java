package org.poo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class User {
    private String firstName;
    private String lastName;
    private String email;
}
