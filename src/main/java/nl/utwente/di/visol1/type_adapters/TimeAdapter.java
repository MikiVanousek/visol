package nl.utwente.di.visol1.type_adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Time;

public class TimeAdapter extends XmlAdapter<Long, Time> {
    @Override
    public Long marshal(Time time) {
        return time == null ? null : time.getTime();
    }

    @Override
    public Time unmarshal(Long value) {
        return value == null ? null : new Time(value);
    }
}
