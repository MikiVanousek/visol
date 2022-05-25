package nl.utwente.di.visol1.type_adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;

public class TimestampAdapter  extends XmlAdapter<Long, Timestamp> {
    @Override
    public Long marshal(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }

    @Override
    public Timestamp unmarshal(Long value) {
        return value == null ? null : new Timestamp(value);
    }
}