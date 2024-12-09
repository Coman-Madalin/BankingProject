package org.poo.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Setter
@Getter
public final class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts = new ArrayList<>();
}
