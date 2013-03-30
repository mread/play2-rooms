package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Configuration extends Model {
    public static Finder<String, Configuration> find = new Finder<String, Configuration>(
            String.class, Configuration.class
    );
    @Id
    private String identifier;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Room> rooms;

    public String getIdentifier() {
        return identifier;
    }

    private void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    private void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("Configuration{");
        out.append("identifier='").append(identifier).append("'");
        out.append(", rooms=[");
        for (int i = 0; i < rooms.size(); i++) {
            out.append(rooms.get(i).toString());
            if (i < rooms.size() -1) out.append(", ");
        }
        out.append("]}");
        return out.toString();
    }
}
