package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private String name;
    private String email;
    private String passwordHash;

    private Role role;
    private int terminalId;
    private int portId;

    public User(String name, String email, String passwordHash, Role role, int terminalId, int portId) {
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

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public Terminal getTerminal() {
        return null; // TerminalDao.getById(terminalId);
    }

    public void setTerminal(Terminal terminal) {
        this.terminalId = terminal.getId();
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public Port getPort() {
        return null; // PortDao.getById(portId);
    }

    public void setPort(Port port) {
        this.portId = port.getId();
    }
}
