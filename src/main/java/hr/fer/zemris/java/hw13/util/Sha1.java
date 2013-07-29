package hr.fer.zemris.java.hw13.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Razred za računanje sažetka poruke.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class Sha1 {

	/**
	 * Sažetak poruke u hexadecimalnom obliku.
	 * 
	 * @param plain
	 *            jasan tekst
	 * @return sažetak zapisan u hex obliku
	 */
	public static String hashHexEncode(final String plain) {

		return binary2Hex(calcHash(plain));

	}

	/**
	 * Izračun sažetka.
	 * 
	 * @param plain
	 *            jasna poruka
	 * @return sažeta poruka
	 */
	private static byte[] calcHash(final String plain) {

		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("SHA1");
		} catch (final NoSuchAlgorithmException ignorable) {
		}

		byte[] msgDigestByte = null;
		try {
			msgDigestByte = msgDigest.digest(plain.getBytes("UTF-8"));
		} catch (final UnsupportedEncodingException ignorable) {
		}

		return msgDigestByte;

	}

	/**
	 * Pretvaranje binarnog niza u hexa niz.
	 * 
	 * @param array
	 *            binaran niz
	 * @return hexa niz
	 */
	private static String binary2Hex(final byte[] array) {

		final String HEXES = "0123456789ABCDEF";

		if (array == null) {
			return null;
		}

		final StringBuilder hex = new StringBuilder(2 * array.length);
		for (final byte b : array) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

}
