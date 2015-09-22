/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static eu.dunst.dukescript.SampleDuke.KundeModel.EingabeStatus.*;


@Model(className = "Customer", targetId = "", properties = {
        @Property(name = "beruf", type = String.class),
        @Property(name = "anzahlKinder", type = String.class),
        @Property(name = "bruttoJahresEinkommen", type = String.class),
        @Property(name = "nettoJahresEinkommen", type = String.class),
        @Property(name = "berechnet", type = boolean.class),
        @Property(name = "inputStatus", type = int.class),
        @Property(name = "fieldBrutto", type = boolean.class),
        @Property(name = "fieldNetto", type = boolean.class)
})
public class KundeModel {

    @ComputedProperty
    static List<String> validate(String beruf) {
        final List<String> error = new ArrayList<>();
        if (beruf != null && beruf.trim().isEmpty()) {
            error.add("beruf");
        }
        if (beruf != null && beruf.trim().length() > 10) {
            error.add("beruf");
        }
        return error;
    }

    @OnPropertyChange("bruttoJahresEinkommen")
    static void bruttoJahresEinkommenChange(Customer customer) {
        final int inputStatus = customer.getInputStatus();
        final String bruttoJahresEinkommen = customer.getBruttoJahresEinkommen();
        if (BN == inputStatus) {
            customer.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            customer.setNettoJahresEinkommen("");
            customer.setInputStatus(B);
        }
        if (B == inputStatus) {
            if (bruttoJahresEinkommen.length() == 0) {
                customer.setInputStatus(BN);
            }
        }
    }

    @OnPropertyChange("inputStatus")
    static void inputstatus(Customer customer) {
        int inputStatus = customer.getInputStatus();
        if (inputStatus == 1) {
            customer.setFieldBrutto(true);
            customer.setFieldNetto(true);
        }
        if (inputStatus == 2) {
            customer.setFieldBrutto(true);
            customer.setFieldNetto(false);
        }
        if (inputStatus == 3) {
            customer.setFieldBrutto(false);
            customer.setFieldNetto(true);
        }
    }

    @OnPropertyChange("nettoJahresEinkommen")
    static void nettoJahresEinkommenChange(Customer customer) {
        final int inputStatus = customer.getInputStatus();
        final String nettoJahresEinkommen = customer.getNettoJahresEinkommen();
        if (BN == inputStatus) {
            customer.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            customer.setBruttoJahresEinkommen("");
            customer.setInputStatus(N);
        }
        if (N == inputStatus) {
            if (nettoJahresEinkommen.length() == 0) {
                customer.setInputStatus(BN);
            }
        }
    }

    @Function
    static void calculateBruttoNetto(Customer customer) {
        if (B == customer.getInputStatus()) {
            final String bruttoJahresEinkommen = customer.getBruttoJahresEinkommen().trim();
            if (bruttoJahresEinkommen.length() != 0) {
                final BigDecimal brutto = new BigDecimal(bruttoJahresEinkommen);
                customer.setNettoJahresEinkommen("" + brutto.multiply(BigDecimal.valueOf(0.42)));
                customer.setInputStatus(BN);
            }
        }
        if (N == customer.getInputStatus()) {
            final String nettoJahresEinkommen = customer.getNettoJahresEinkommen().trim();
            if (nettoJahresEinkommen.length() != 0) {
                final BigDecimal brutto = new BigDecimal(nettoJahresEinkommen);
                customer.setBruttoJahresEinkommen("" + brutto.multiply(BigDecimal.valueOf(0.42)));
                customer.setInputStatus(BN);
            }
        }
    }

    public enum EingabeStatus {
        PLATZHALTER;
        public static final int VALUE_CHANGE_WITHOUT_NOTIFICATION = 0;
        public static final int BN = 1;
        public static final int B = 2;
        public static final int N = 3;
    }
}


