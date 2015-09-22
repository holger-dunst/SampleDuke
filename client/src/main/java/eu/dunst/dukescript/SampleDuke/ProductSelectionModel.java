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

import static eu.dunst.dukescript.SampleDuke.ProductSelectionStatus.NO_SELECTION;

/**
 * @author Holger
 */
@Model(className = "ProductSelection", targetId = "", properties = {
        @Property(name = "produkt1", type = ProductSelectionStatus.class),
        @Property(name = "produkt2", type = ProductSelectionStatus.class),
        @Property(name = "produkt3", type = ProductSelectionStatus.class)
})
class ProductSelectionModel {

    @ComputedProperty
    static List<String> validate(ProductSelectionStatus produkt1, ProductSelectionStatus produkt2, ProductSelectionStatus produkt3) {
        final List<String> error = new ArrayList<>();
        if (produkt1 == NO_SELECTION) {
            error.add("produkt1");
        }
        if (produkt2 == NO_SELECTION) {
            error.add("produkt2");
        }
        if (produkt3 == NO_SELECTION) {
            error.add("produkt3");
        }
        return error;
    }

}
