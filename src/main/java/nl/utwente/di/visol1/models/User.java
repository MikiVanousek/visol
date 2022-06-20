package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Comparable<User>{
	private String name;
	private String email;
	@XmlElement(name = "password")
	@JsonProperty("password")
	private String passwordHash;
	@XmlElement(name = "salt_and_pepper")
	@JsonProperty("salt_and_pepper")
	private String saltAndPepper;

	private Role role;
	private Integer terminalId;
	private Integer portId;

	public User(String name, String email, String passwordHash, String saltAndPepper, Role role, Integer terminalId, Integer portId) {
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.saltAndPepper = saltAndPepper;
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

	public String getSaltAndPepper() {
		return saltAndPepper;
	}

	public void setSaltAndPepper(String saltAndPepper) {
		this.saltAndPepper = saltAndPepper;
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
		return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash)
		       && Objects.equals(saltAndPepper, user.saltAndPepper) && role == user.role && Objects.equals(terminalId, user.terminalId)
		       && Objects.equals(portId, user.portId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, passwordHash, saltAndPepper, role, terminalId, portId);
	}

	@Override
	public int compareTo(User other) {
		return email.compareTo(other.email);
	}
}
