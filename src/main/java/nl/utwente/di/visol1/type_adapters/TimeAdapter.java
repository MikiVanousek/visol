package nl.utwente.di.visol1.type_adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Time;

public class TimeAdapter extends XmlAdapter<String, Time> {
    @Override
    public String marshal(Time time) {
        return time == null ? null : time.toString();
    }

    @Override
    public Time unmarshal(String value) {
        return value == null ? null : Time.valueOf(value);
    }
}
