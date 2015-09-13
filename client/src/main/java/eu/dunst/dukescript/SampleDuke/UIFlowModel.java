/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.dunst.dukescript.SampleDuke;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.ModelOperation;
import net.java.html.json.OnPropertyChange;
import net.java.html.json.Property;

@Model(className = "FlowModel", properties = {
    @Property(name = "page", type = String.class)})
class UIFlowModel {

    public static final String PAGE1 = "Page1";
    public static final String PAGE2 = "Page2";

    @OnPropertyChange("page")
    static void flowChange(FlowModel flow) {
        System.err.println("New page: " + flow.getPage());
    }

    @ModelOperation
    @Function
    public static void next(FlowModel flowModel) {
        if (PAGE1.equals(flowModel.getPage())) {
            
            flowModel.setPage(PAGE2);
        }
    }

    @ModelOperation
    @Function
    public static void back(FlowModel flowModel) {
        if (PAGE2.equals(flowModel.getPage())) {
            flowModel.setPage(PAGE1);
        }
    }

}
