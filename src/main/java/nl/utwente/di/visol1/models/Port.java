package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Port {

    private int id;
    private String name;

    public Port(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
