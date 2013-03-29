package controllers;

import org.codehaus.jackson.JsonNode;
import play.data.validation.Constraints;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.quicksetup;

public class QuickSetup extends Controller {

    public static Result index() {
        return ok(quicksetup.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode json = request().body().asJson();
        return ok(json.toString());
    }

    public static class Room {
        @Constraints.Required
        public String name;
    }
}
