package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CommerciantInput;

import java.util.List;

@Getter
@Setter
public class Commerciants {
    private int id;
    private String description;
    private List<String> commerciants;

    public Commerciants(CommerciantInput commerciants) {
        this.id = commerciants.getId();
        this.description = commerciants.getDescription();
        this.commerciants = commerciants.getCommerciants();
    }
}
