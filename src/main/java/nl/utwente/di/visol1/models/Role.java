package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum Role {
    @XmlEnumValue("manager") MANAGER,
    @XmlEnumValue("planner") PLANNER,
    @XmlEnumValue("authority") AUTHORITY
}
