package nl.utwente.di.visol1.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionUtil {
	private static final String HASH_ALGORITHM = "SHA3-256";
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String OUTPUT_FORMAT = "%-20s: %s%n";

	private static byte[] digest(byte[] input) {
		try {
			return MessageDigest.getInstance(EncryptionUtil.HASH_ALGORITHM).digest(input);
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalArgumentException(exception);
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	private static byte[] hexToBytes(String bytes) {
		String[] hexes = bytes.split("(?<=\\G..)");
		byte[] result = new byte[hexes.length];
		for (int i = 0; i < hexes.length; i++) {
			result[i] = (byte) Integer.parseInt(hexes[i], 16);
		}
		return result;
	}

	private static byte[] generateSaltOrPepper() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		return bytes;
	}

	private static byte[] hash(byte[] password, byte[] salt, byte[] pepper) {
		byte[] input = new byte[password.length + salt.length + pepper.length];
		System.arraycopy(password, 0, input, 0, password.length);
		System.arraycopy(salt, 0, input, password.length, salt.length);
		System.arraycopy(pepper, 0, input, password.length + salt.length, pepper.length);
		return EncryptionUtil.digest(input);
	}

	public static EncryptedPassword encryptPassword(String password) {
		byte[] passwordBytes = password.getBytes(UTF_8);
		byte[] salt = generateSaltOrPepper();
		byte[] pepper = generateSaltOrPepper();

		byte[] saltAndPepper = new byte[salt.length + pepper.length];
		System.arraycopy(salt, 0, saltAndPepper, 0, salt.length);
		System.arraycopy(pepper, 0, saltAndPepper, salt.length, pepper.length);

		byte[] hash = hash(passwordBytes, salt, pepper);
		return new EncryptedPassword(bytesToHex(hash), bytesToHex(saltAndPepper));
	}

	public static String checkPassword(String password, String saltAndPepper) {
		byte[] saltAndPepperBytes = hexToBytes(saltAndPepper);
		byte[] passwordBytes = password.getBytes(UTF_8);

		byte[] salt = new byte[saltAndPepperBytes.length / 2];
		System.arraycopy(saltAndPepperBytes, 0, salt, 0, salt.length);

		byte[] pepper = new byte[saltAndPepperBytes.length / 2];
		System.arraycopy(saltAndPepperBytes, salt.length, pepper, 0, pepper.length);

		byte[] hash = hash(passwordBytes, salt, pepper);
		return bytesToHex(hash);
	}

	public static void main(String[] args) {
		String password = "password";
		EncryptedPassword encryptedPassword = EncryptionUtil.encryptPassword(password);
		System.out.printf(OUTPUT_FORMAT, "Hash", encryptedPassword.hash);
		System.out.printf(OUTPUT_FORMAT, "Salt and pepper", encryptedPassword.saltAndPepper);

		String checkHash = EncryptionUtil.checkPassword(password, encryptedPassword.saltAndPepper);
		System.out.printf(OUTPUT_FORMAT, "Check hash", checkHash);
	}

	public static class EncryptedPassword {
		public final String hash;
		public final String saltAndPepper;

		private EncryptedPassword(String hash, String saltAndPepper) {
			this.hash = hash;
			this.saltAndPepper = saltAndPepper;
		}
	}
}
