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


@Model(className = "Kunde", targetId = "", properties = {
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
    static void bruttoJahresEinkommenChange(Kunde kunde) {
        final int inputStatus = kunde.getInputStatus();
        final String bruttoJahresEinkommen = kunde.getBruttoJahresEinkommen();
        if (BN == inputStatus) {
            kunde.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            kunde.setNettoJahresEinkommen("");
            kunde.setInputStatus(B);
        }
        if (B == inputStatus) {
            if (bruttoJahresEinkommen.length() == 0) {
                kunde.setInputStatus(BN);
            }
        }
    }

    @OnPropertyChange("inputStatus")
    static void inputstatus(Kunde kunde) {
        int inputStatus = kunde.getInputStatus();
        if (inputStatus == 1) {
            kunde.setFieldBrutto(true);
            kunde.setFieldNetto(true);
        }
        if (inputStatus == 2) {
            kunde.setFieldBrutto(true);
            kunde.setFieldNetto(false);
        }
        if (inputStatus == 3) {
            kunde.setFieldBrutto(false);
            kunde.setFieldNetto(true);
        }
    }

    @OnPropertyChange("nettoJahresEinkommen")
    static void nettoJahresEinkommenChange(Kunde kunde) {
        final int inputStatus = kunde.getInputStatus();
        final String nettoJahresEinkommen = kunde.getNettoJahresEinkommen();
        if (BN == inputStatus) {
            kunde.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            kunde.setBruttoJahresEinkommen("");
            kunde.setInputStatus(N);
        }
        if (N == inputStatus) {
            if (nettoJahresEinkommen.length() == 0) {
                kunde.setInputStatus(BN);
            }
        }
    }

    @Function
    static void calculateBruttoNetto(Kunde kunde) {
        if (B == kunde.getInputStatus()) {
            final String bruttoJahresEinkommen = kunde.getBruttoJahresEinkommen().trim();
            if (bruttoJahresEinkommen.length() != 0) {
                final BigDecimal brutto = new BigDecimal(bruttoJahresEinkommen);
                kunde.setNettoJahresEinkommen("" + brutto.multiply(BigDecimal.valueOf(0.42)));
                kunde.setInputStatus(BN);
            }
        }
        if (N == kunde.getInputStatus()) {
            final String nettoJahresEinkommen = kunde.getNettoJahresEinkommen().trim();
            if (nettoJahresEinkommen.length() != 0) {
                final BigDecimal brutto = new BigDecimal(nettoJahresEinkommen);
                kunde.setBruttoJahresEinkommen("" + brutto.multiply(BigDecimal.valueOf(0.42)));
                kunde.setInputStatus(BN);
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


