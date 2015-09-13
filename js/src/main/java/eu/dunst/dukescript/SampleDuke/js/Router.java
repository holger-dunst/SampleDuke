/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.dunst.dukescript.SampleDuke.js;

import net.java.html.js.JavaScriptBody;
import net.java.html.js.JavaScriptResource;

@JavaScriptResource(value = "registerRouter.js")
public final class Router {

    private Router() {
    }

    @JavaScriptBody(
            args = {}, body
            = ""
    )
    public static native String registerBinding();
}
