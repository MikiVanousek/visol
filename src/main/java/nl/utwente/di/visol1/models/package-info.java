@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = TimeAdapter.class, type = Time.class),
        @XmlJavaTypeAdapter(value = TimestampAdapter.class, type = Timestamp.class)
})
package nl.utwente.di.visol1.models;

import nl.utwente.di.visol1.type_adapters.TimeAdapter;
import nl.utwente.di.visol1.type_adapters.TimestampAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.sql.Time;
import java.sql.Timestamp;