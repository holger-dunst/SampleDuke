/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.ComputedProperty;
import net.java.html.json.Model;
import net.java.html.json.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Holger
 */
@Model(className = "ProduktAuswahl", targetId = "", properties = {
        @Property(name = "produkt1", type = String.class),
        @Property(name = "produkt2", type = String.class),
        @Property(name = "produkt3", type = String.class)
})
class ProduktAuswahlModel {

    @ComputedProperty
    static List<String> validate(String produkt1, String produkt2, String produkt3) {
        final List<String> error = new ArrayList<>();
        if (produkt1 == null) {
            error.add("produkt1");
        }
        if (produkt2 == null) {
            error.add("produkt2");
        }
        if (produkt3 == null) {
            error.add("produkt3");
        }
        return error;
    }

}
