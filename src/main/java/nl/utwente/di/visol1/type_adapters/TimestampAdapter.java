package nl.utwente.di.visol1.type_adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
    @Override
    public String marshal(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toString();
    }

    @Override
    public Timestamp unmarshal(String value) {
        return value == null ? null : Timestamp.valueOf(value);
    }
}