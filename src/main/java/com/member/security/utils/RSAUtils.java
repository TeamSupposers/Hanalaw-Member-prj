package com.member.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtils {

 
	// Key Section
	/**
	 * ����Ű/����Ű KeyPair�� �����մϴ�.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static KeyPair createKeyPair(int keySize) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		//keyPairGen.initialize(2048);
		keyPairGen.initialize(keySize);
		return keyPairGen.genKeyPair();
	}// :

	/**
	 * ����Ű�� byte[]�� ����Ű�� �����մϴ�.
	 * 
	 * @param keyBytes ����Ű�� ����Ʈ�迭
	 * @return ����Ű
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws Exception                the Exception
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}// :

	/**
	 * ����Ű�� ����Ʈ �迭�κ��� ����Ű�� �����Ѵ�.
	 * 
	 * @param keyBytes ����Ű�� ����Ʈ �迭
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * ����Ű�� Base64�� encoding�Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param keyPair KeyPair �ν��Ͻ�
	 * @return Base64�� ���ڵ��� ���ڿ�
	 */
	public static String privateKeyToBase64(KeyPair keyPair) {
		byte[] privBytes = keyPair.getPrivate().getEncoded();
		return new String(java.util.Base64.getEncoder().encode(privBytes));
	}// :

	/**
	 * ����Ű�� Base64�� encoding�Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param keyPair KeyPair �ν��Ͻ�
	 * @return Base64�� ���ڵ��� ���ڿ�
	 */
	public static String publicKeyToBase64(KeyPair keyPair) {
		byte[] privBytes = keyPair.getPublic().getEncoded();
		return new String(java.util.Base64.getEncoder().encode(privBytes));
	}// :

	/**
	 * Base64�� ���ڵ��� ���ڿ��� PrivateKey�� ��ȯ�Ѵ�.
	 * 
	 * @param encodedPrivateKey
	 * @return PrivateKey �ν��Ͻ�
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static PrivateKey base64ToPrivateKey(String encodedPrivateKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] decodedByte = Base64.getDecoder().decode(encodedPrivateKey.getBytes());
		return getPrivateKey(decodedByte);
	}

	/**
	 * Base64�� ���ڵ��� ���ڿ��� PublicKey�� ��ȯ�Ѵ�.
	 * 
	 * @param encodedPublicKey ���ڵ��� ���ڿ�
	 * @return PublicKey �ν��Ͻ�
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey base64ToPublicKey(String encodedPublicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] decodedByte = Base64.getDecoder().decode(encodedPublicKey.getBytes());
		return getPublicKey(decodedByte);
	}

	// encrypt/decryt section

	/**
	 * ����Ű�� ����Ʈ�� ��ȣȭ �Ѵ�.
	 * 
	 * @param bytesToEncrypt ��ȣȭ�� ����Ʈ �迭
	 * @param publicKey      ����Ű
	 * @return ��ȣȭ�� ����Ʈ �迭
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] bytesToEncrypt, PublicKey publicKey) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// Perform the actual encryption on those bytes
		byte[] cipherText = cipher.doFinal(bytesToEncrypt);
		return cipherText;
	}// :

	/**
	 * ����Ű�� ��ȣȭ�� ����Ʈ �迭�� ��ȣȭ �Ѵ�.
	 * 
	 * @param bytesToDecrypt ��ȣȭ �� ����Ʈ �迭
	 * @param privateKey     ����Ű
	 * @return ��ȣȭ�� ����Ʈ �迭
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] bytesToDecrypt, PrivateKey privateKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(bytesToDecrypt);
	}// :

	/**
	 * ���ڿ��� ��ȣȭ�� �� Base64�� encodging �Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param strToEncrypt     ��ȣȭ�� ���ڿ�
	 * @param base64PublicKey Base64�� ���ڵ��� ����Ű ���ڿ�
	 * @return Base64�� ��ȯ�� ��ȣȭ�� ���ڿ�
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 */
	public static String encrypt(String strToEncrypt, String base64PublicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchPaddingException {
		PublicKey pubKey = base64ToPublicKey(base64PublicKey); // Base64�� ���ڵ��� ���ڿ��� ����Ű�� ��ȯ
		byte[] encryptedByte = encrypt(strToEncrypt.getBytes(), pubKey); // ��ȣȭ
		return new String(Base64.getEncoder().encode(encryptedByte)); // Base64 ��ȯ
	}// :

	/**
	 * Base64�� ���ڵ��� ��ȣȭ�� ���ڿ��� ���ڿ��� ��ȯ�Ѵ�. 
	 * @param strToDecrypt Base64�� ���ڵ��� ��ȣȭ�� ���ڿ�
	 * @param base64PrivKey Base64�� ���ڵ��� ��ȣȭ�� ����Ű 
	 * @return
	 * 		��ȣȭ�� ���ڿ�
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decrypt(String strToDecrypt, String base64PrivKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		PrivateKey privKey = base64ToPrivateKey(base64PrivKey); // Base64�� ���ڵ��� ���ڿ��� ����Ű�� ��ȯ
		byte[] encryptedByte = Base64.getDecoder().decode(strToDecrypt); // Base64 decoding
		byte[] decryptedByte = decrypt(encryptedByte, privKey); // ��ȣȭ 
		return new String(decryptedByte);
	}// :

	// utility

	/**
	 * ����Ű/����Ű�� ���Ϸ� ����. ����Ű�� ��ũ�� ���� String publicKeyFile = "/usr/home/key";
	 * RSAUtils.saveKeyAsFile(keyPair.getPublic().getEncoded(), publicKeyFile);
	 * 
	 * ����Ű�� ��ũ�� ���� String privateKeyFile = "/usr/home/key";
	 * RSAUtils.saveKeyAsFile(keyPair.getPrivate().getEncoded(), privateKeyFile);
	 *
	 * @param keyBytes Ű�� ����Ʈ�迭
	 * @param filePath ������ ���ϸ�(Ǯ�н�)
	 * @throws IOException
	 * @throws Exception   the Exception
	 */
	public static void saveKeyAsFile(byte[] keyBytes, String filePath) throws IOException {
		// ���� �ý��ۿ� ��ȣȭ�� ����Ű�� ����.
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(keyBytes);
		fos.close();
	}// :

	/**
	 * ����Ű�� ���Ϸ� ����
	 * 
	 * @param keyPair
	 * @param filePath
	 * @throws IOException
	 */
	public static void savePublicKeyAsFile(KeyPair keyPair, String filePath) throws IOException {
		saveKeyAsFile(keyPair.getPublic().getEncoded(), filePath);
	}// :

	/**
	 * ����Ű�� ���Ϸ� ����
	 * 
	 * @param keyPair
	 * @param filePath
	 * @throws IOException
	 */
	public static void savePrivateKeyAsFile(KeyPair keyPair, String filePath) throws IOException {
		saveKeyAsFile(keyPair.getPrivate().getEncoded(), filePath);
	}// :

	/**
	 * ����Ű/����Ű�� ���Ϸ� ���� �о���δ�.
	 * 
	 * @param filePath ����� ���ϸ�(Ǯ�н�)
	 * @return Ű�� ����Ʈ�迭
	 * @throws Exception the Exception
	 */
	public static byte[] getKeyFromFile(String filePath) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int theByte = 0;
		while ((theByte = fis.read()) != -1) {
			baos.write(theByte);
		}
		fis.close();
		byte[] keyBytes = baos.toByteArray();
		baos.close();
		return keyBytes;
	}// :
 
}