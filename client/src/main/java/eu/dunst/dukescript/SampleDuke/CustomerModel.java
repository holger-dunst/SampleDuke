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

import static eu.dunst.dukescript.SampleDuke.CustomerModel.EingabeStatus.*;


@Model(className = "Customer", targetId = "", properties = {
        @Property(name = "job", type = String.class),
        @Property(name = "anzahlKinder", type = String.class),
        @Property(name = "grossAnnualIncome", type = String.class),
        @Property(name = "netAnnualIncome", type = String.class),
        @Property(name = "berechnet", type = boolean.class),
        @Property(name = "inputStatus", type = int.class),
        @Property(name = "fieldBrutto", type = boolean.class),
        @Property(name = "fieldNetto", type = boolean.class)
})
public class CustomerModel {

    @ComputedProperty
    static List<String> validate(String job) {
        final List<String> error = new ArrayList<>();
        if (job != null && job.trim().isEmpty()) {
            error.add("job");
        }
        if (job != null && job.trim().length() > 10) {
            error.add("job");
        }
        return error;
    }

    @OnPropertyChange("grossAnnualIncome")
    static void grossAnnualIncomeChange(Customer customer) {
        final int inputStatus = customer.getInputStatus();
        final String grossAnnualIncome = customer.getGrossAnnualIncome();
        if (BN == inputStatus) {
            customer.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            customer.setNetAnnualIncome("");
            customer.setInputStatus(B);
        }
        if (B == inputStatus) {
            if (grossAnnualIncome.length() == 0) {
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

    @OnPropertyChange("netAnnualIncome")
    static void netAnnualIncomeChange(Customer customer) {
        final int inputStatus = customer.getInputStatus();
        final String netAnnualIncome = customer.getNetAnnualIncome();
        if (BN == inputStatus) {
            customer.setInputStatus(VALUE_CHANGE_WITHOUT_NOTIFICATION);
            customer.setGrossAnnualIncome("");
            customer.setInputStatus(N);
        }
        if (N == inputStatus) {
            if (netAnnualIncome.length() == 0) {
                customer.setInputStatus(BN);
            }
        }
    }

    @Function
    static void calculateBruttoNetto(Customer customer) {
        if (B == customer.getInputStatus()) {
            final String grossAnnualIncome = customer.getGrossAnnualIncome().trim();
            if (grossAnnualIncome.length() != 0) {
                final BigDecimal brutto = new BigDecimal(grossAnnualIncome);
                customer.setNetAnnualIncome("" + brutto.multiply(BigDecimal.valueOf(0.42)));
                customer.setInputStatus(BN);
            }
        }
        if (N == customer.getInputStatus()) {
            final String netAnnualIncome = customer.getNetAnnualIncome().trim();
            if (netAnnualIncome.length() != 0) {
                final BigDecimal brutto = new BigDecimal(netAnnualIncome);
                customer.setGrossAnnualIncome("" + brutto.multiply(BigDecimal.valueOf(0.42)));
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


