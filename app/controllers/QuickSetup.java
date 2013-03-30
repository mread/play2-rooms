package controllers;

import models.Configuration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.quicksetup;

import java.io.IOException;

public class QuickSetup extends Controller {

    public static Result index() {
        return ok(quicksetup.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() throws IOException {
        JsonNode json = request().body().asJson();

        ObjectMapper mapper = new ObjectMapper();
        Configuration configuration = mapper.readValue(json, Configuration.class);

        if (configuration.find.byId(configuration.getIdentifier()) != null) {
            return status(409, "Identifier already exists");
        }

        configuration.save();

        ObjectNode result = Json.newObject();
        result.put("message", "Saved");
        return ok(result);
    }

    public static class Room {
        @Constraints.Required
        public String name;
    }

}
