package org.poo.commerciant;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class Commerciant {
    private int id;
    private String description;
    private List<String> commerciants;
}
