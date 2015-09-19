package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

import java.util.List;

import static eu.dunst.dukescript.SampleDuke.KundeModel.EingabeStatus.BN;
import static eu.dunst.dukescript.SampleDuke.ProductSelectionStatus.NO_SELECTION;
import static eu.dunst.dukescript.SampleDuke.UIFlowModel.PAGE1;
import static eu.dunst.dukescript.SampleDuke.UIFlowModel.PAGE2;

@Model(className = "Beratung", targetId = "", properties = {
        @Property(name = "kunde", type = Kunde.class),
        @Property(name = "produktAuswahl", type = ProduktAuswahl.class),
        @Property(name = "flow", type = FlowModel.class),
        @Property(name = "error", type = boolean.class),
        @Property(name = "msg", type = String.class),
        @Property(name = "current", type = String.class),
        @Property(name = "bindingErrors", type = String.class, array = true)
})
final class DataModel {

    private static Beratung ui;

    @Function
    static void validate(Beratung beratung) {
        boolean error = false;
        if (PAGE1.equals(beratung.getFlow().getPage())) {
            final List<String> validate = beratung.getProduktAuswahl().getValidate();
            error = checkValidation(beratung, validate);
        }
        if (PAGE2.equals(beratung.getFlow().getPage())) {
            final List<String> validate = beratung.getKunde().getValidate();
            error = checkValidation(beratung, validate);
        }
        System.out.printf("page: %s\n", beratung.getFlow().getPage());
        if (!error) {
            beratung.getFlow().next();
        }
    }

    private static boolean checkValidation(Beratung beratung, List<String> validate) {
        boolean error;
        if (!validate.isEmpty()) {
            beratung.setMsg("Bitte überprüfen Sie Ihre Eingaben");
            beratung.getBindingErrors().clear();
            beratung.getBindingErrors().addAll(validate);
            beratung.setError(true);
            error = true;
        } else {
            beratung.setMsg("Kein Fehler");
            beratung.setError(false);
            error = false;
            beratung.getBindingErrors().clear();
        }
        return error;
    }

    /**
     * Called when the page is ready.
     */
    static void onPageLoad() throws Exception {
        ui = new Beratung();
        ui.getFlow().setPage("Page1");
        ui.setCurrent(PAGE1);
        ui.setError(false);
        ui.setMsg("");
        // Models.toRaw(ui);
        //    Router.registerBinding();
        //
        final Kunde kunde = new Kunde();
        kunde.setAnzahlKinder("2");
        kunde.setBeruf("Kellner");
        //kunde.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
        kunde.setBruttoJahresEinkommen("60000.00");
        kunde.setNettoJahresEinkommen("35000.00");
        kunde.setInputStatus(BN);
        //
        ui.setKunde(kunde);
        final ProduktAuswahl produktAuswahl = new ProduktAuswahl();
        produktAuswahl.setProdukt1(NO_SELECTION);
        produktAuswahl.setProdukt2(NO_SELECTION);
        produktAuswahl.setProdukt3(NO_SELECTION);
        ui.setProduktAuswahl(produktAuswahl);
        ui.applyBindings();
    }
}
