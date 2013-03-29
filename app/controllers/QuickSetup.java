package controllers;

import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.quicksetup;

import static play.data.Form.form;

public class QuickSetup extends Controller {

    public static Result index() {
        return ok(quicksetup.render());
    }

    public static Result addRoom() {
        return ok();
    }

    public static class Room {
        @Constraints.Required
        public String name;
    }
}
