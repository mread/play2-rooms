package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Configuration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints;
import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.quicksetup;

import java.io.IOException;

import static akka.pattern.Patterns.ask;

public class QuickSetup extends Controller {

    private static ActorRef myActor = Akka.system().actorOf(new Props(MyActor.class));
    private static ObjectMapper mapper = new ObjectMapper();

    public static Result index() {
        return ok(quicksetup.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() throws IOException {
        JsonNode json = request().body().asJson();
        Configuration configuration = mapper.readValue(json, Configuration.class);

        return async(Akka.asPromise(ask(myActor, configuration, 1000)).map(
                new F.Function<Object, Result>() {
                    public Result apply(Object response) {
                        return (Result) response;
                    }
                }
        ));
    }

    public static class Room {
        @Constraints.Required
        public String name;
    }

    private static class MyActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof Configuration) {
                Configuration configuration = (Configuration) message;
                if (configuration.find.byId(configuration.getIdentifier()) != null) {
                    getSender().tell(status(409, "Identifier already exists"));
                    return;
                }
                configuration.save();
                ObjectNode result = Json.newObject();
                result.put("message", "Saved");
                getSender().tell(ok(result));
            } else {
                unhandled(message);
            }
        }
    }
}
