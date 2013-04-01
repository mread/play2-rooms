package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Akka;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.util.List;

import static akka.pattern.Patterns.ask;

public class Rooms extends Controller {

    private static ActorRef myActor = Akka.system().actorOf(new Props(MyActor.class));
    private static ObjectMapper mapper = new ObjectMapper();

    @BodyParser.Of(BodyParser.Json.class)
    public static Result list() throws IOException {
        return async(Akka.asPromise(ask(myActor, "list the rooms", 1000)).map(
                new F.Function<Object, Result>() {
                    public Result apply(Object response) {
                        return (Result) response;
                    }
                }
        ));
    }

    private static class MyActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof String) {

                List<Room> rooms = Room.find.select("id, roomName")
//                        .fetch("configuration")
                        .findList();
                ObjectNode result = Json.newObject();
                result.put("Result", "OK");
                result.put("Records", Json.toJson(rooms));
                getSender().tell(ok(result), myActor);
            } else {
                unhandled(message);
            }
        }
    }
}
