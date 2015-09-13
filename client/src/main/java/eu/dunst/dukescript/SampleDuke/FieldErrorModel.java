package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.Model;
import net.java.html.json.Property;

@Model(className = "FieldError", targetId = "", properties = {
        @Property(name = "fieldname", type = String.class),
        @Property(name = "msg", type = String.class)
})
final class FieldErrorModel {
}
