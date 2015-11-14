package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Models;
import net.java.html.json.Property;

import java.util.List;

import static eu.dunst.dukescript.SampleDuke.CustomerModel.EingabeStatus.BN;
import static eu.dunst.dukescript.SampleDuke.ProductSelectionStatus.NO_SELECTION;
import static eu.dunst.dukescript.SampleDuke.UIFlowModel.PAGE1;
import static eu.dunst.dukescript.SampleDuke.UIFlowModel.PAGE2;

@Model(className = "Session", targetId = "", properties = {
        @Property(name = "customer", type = Customer.class),
        @Property(name = "productSelection", type = ProductSelection.class),
        @Property(name = "flow", type = FlowModel.class),
        @Property(name = "error", type = boolean.class),
        @Property(name = "msg", type = String.class),
        @Property(name = "current", type = String.class),
        @Property(name = "bindingErrors", type = String.class, array = true)
})
final class DataModel {

    private static Session ui;

    @Function
    static void validate(Session session) {
        System.out.println("ui = " + ui);
        boolean error = false;
        if (PAGE1.equals(session.getFlow().getPage())) {
            final List<String> validate = session.getProductSelection().getValidate();
            error = checkValidation(session, validate);
        }
        if (PAGE2.equals(session.getFlow().getPage())) {
            final List<String> validate = session.getCustomer().getValidate();
            error = checkValidation(session, validate);
        }
//        System.out.printf("page: %s\n", session.getFlow().getPage());
        if (!error) {
            session.getFlow().next();
        }
    }

    private static boolean checkValidation(Session session, List<String> validate) {
        boolean error;
        if (!validate.isEmpty()) {
            session.setMsg("Validation Error: Please check.");
            session.getBindingErrors().clear();
            session.getBindingErrors().addAll(validate);
            session.setError(true);
            error = true;
        } else {
            session.setMsg("");
            session.setError(false);
            error = false;
            session.getBindingErrors().clear();
        }
        return error;
    }

    /**
     * Called when the page is ready.
     */
    static void onPageLoad() throws Exception {
        ui = new Session();
        ui.getFlow().setPage("Page1");
        ui.setCurrent(PAGE1);
        ui.setError(false);
        ui.setMsg("");
        Models.toRaw(ui);
        //    Router.registerBinding();
        //
        final Customer customer = ui.getCustomer();
        customer.setAnzahlKinder("2");
        customer.setJob("Software Engineer");
        //customer.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
        customer.setGrossAnnualIncome("60000.00");
        customer.setNetAnnualIncome("35000.00");
        customer.setInputStatus(BN);
        //
        final ProductSelection productSelection = ui.getProductSelection();
        productSelection.setProdukt1(NO_SELECTION);
        productSelection.setProdukt2(NO_SELECTION);
        productSelection.setProdukt3(NO_SELECTION);
        ui.applyBindings();
    }
}
