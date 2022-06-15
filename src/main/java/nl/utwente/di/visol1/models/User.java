package nl.utwente.di.visol1.models;

import org.jvnet.hk2.annotations.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private String name;
    private String email;
    private String passwordHash;

    private Role role;
    private Integer terminalId;
    private Integer portId;

    public User(String name, String email, String passwordHash, Role role, Integer terminalId, Integer portId) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.terminalId = terminalId;
        this.portId = portId;
    }

    public User() {
        // Empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public Terminal getTerminal() {
        return null; // TerminalDao.getById(terminalId);
    }

    public void setTerminal(Terminal terminal) {
        this.terminalId = terminal == null ? null : terminal.getId();
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public Port getPort() {
        return null; // PortDao.getById(portId);
    }

    public void setPort(Port port) {
        this.portId = port == null ? null : port.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash) && role == user.role && Objects.equals(terminalId, user.terminalId) && Objects.equals(portId, user.portId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, passwordHash, role, terminalId, portId);
    }
}
