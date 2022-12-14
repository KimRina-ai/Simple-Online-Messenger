import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class String_crypto {

	final int N = 624; // MT19937_32 initialization
	final int M = 397;
	final long MATRIX_A = 0x9908b0dfL;
	final long UMASK = 0x80000000L;
	final long LMASK = 0x7fffffffL;

	private byte[] encrypt(String s) {

		byte[] buffer;
		byte[] check_sum = new byte[20];

		buffer = s.getBytes();
		check_sum = checksum(buffer);

		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];

		System.arraycopy(check_sum, 0, seed1, 0, 4); // üũ��������� �õ����
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);

		byte key[] = new byte[16];
		byte IV[] = new byte[16];
		byte tempkey[] = new byte[4];

		tempkey = MT19937(seed1); // key�� �ʱ�ȭ���� ����
		System.arraycopy(tempkey, 0, key, 0, 4);
		System.arraycopy(tempkey, 4, IV, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		System.arraycopy(tempkey, 4, IV, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		System.arraycopy(tempkey, 4, IV, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
		System.arraycopy(tempkey, 4, IV, 12, 4);

		// AES128 ��ȣȭ ���� key = key[]
		Key AES_key = new SecretKeySpec(key, "AES");
		Cipher AES;
		byte[] result = null;

		try {
			AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AES.init(Cipher.ENCRYPT_MODE, AES_key, new IvParameterSpec(IV));
			result = AES.doFinal(buffer);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		byte[] send = new byte[result.length + 20];
		System.arraycopy(result, 0, send, 0, result.length);
		System.arraycopy(check_sum, 0, send, result.length, 20);

		// �������**********************************************************************************
		// System.out.println("_________________________________________________________________");
		// System.out.println("���� :");

		// String s1 = new String(buffer);
		// String s2 = test(check_sum);
		// String s3 = test(key);
		// String s4 = test(IV);
		// String s5 = new String(send);

		// System.out.println(s1);
		// System.out.println("üũ�� : " + s2 );
		// System.out.println("AES_KEY : " + s3 );
		// System.out.println("�ʱ�ȭ���� : " + s4 );
		// System.out.println("����� : " + test(send));
		// System.out.println("��ȣȭ�� ���� :");
		// System.out.println(s5);
		// �������
		// �׽�Ʈ��****************************************************************************

		return send;
	}

	private byte[] fencrypt(byte[] buffer) {
		byte[] check_sum = new byte[20];

		check_sum = checksum(buffer);

		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];

		System.arraycopy(check_sum, 0, seed1, 0, 4); // üũ��������� �õ����
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);

		byte key[] = new byte[16];
		byte IV[] = new byte[16];
		byte tempkey[] = new byte[4];

		tempkey = MT19937(seed1); // key�� �ʱ�ȭ���� ����
		System.arraycopy(tempkey, 0, key, 0, 4);
		System.arraycopy(tempkey, 4, IV, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		System.arraycopy(tempkey, 4, IV, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		System.arraycopy(tempkey, 4, IV, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
		System.arraycopy(tempkey, 4, IV, 12, 4);

		// AES128 ��ȣȭ ���� key = key[]
		Key AES_key = new SecretKeySpec(key, "AES");
		Cipher AES;
		byte[] result = null;

		try {
			AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AES.init(Cipher.ENCRYPT_MODE, AES_key, new IvParameterSpec(IV));
			result = AES.doFinal(buffer);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		byte[] send = new byte[result.length + 20];
		System.arraycopy(result, 0, send, 0, result.length);
		System.arraycopy(check_sum, 0, send, result.length, 20);

		// �������**********************************************************************************
		// System.out.println("_________________________________________________________________");
		// System.out.println("���� :");

		// String s1 = new String(buffer);
		// String s2 = test(check_sum);
		// String s3 = test(key);
		// String s4 = test(IV);
		// String s5 = new String(send);

		// System.out.println(s1);
		// System.out.println("üũ�� : " + s2 );
		// System.out.println("AES_KEY : " + s3 );
		// System.out.println("�ʱ�ȭ���� : " + s4 );
		// System.out.println("����� : " + test(send));
		// System.out.println("��ȣȭ�� ���� :");
		// System.out.println(s5);
		// �������
		// �׽�Ʈ��****************************************************************************

		return send;
	}

	private String decrypt(byte[] b) {
		byte[] check_sum = new byte[20];
		byte[] check_sum_Contrast = new byte[20];
		byte[] s = new byte[b.length - 20];

		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];

		System.arraycopy(b, b.length - 20, check_sum, 0, 20); // üũ���� ����и�
		System.arraycopy(b, 0, s, 0, b.length - 20);

		System.arraycopy(check_sum, 0, seed1, 0, 4); // üũ��������� �õ����
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);

		byte key[] = new byte[16];
		byte IV[] = new byte[16];
		byte tempkey[] = new byte[4];

		tempkey = MT19937(seed1); // key�� �ʱ�ȭ���� ����
		System.arraycopy(tempkey, 0, key, 0, 4);
		System.arraycopy(tempkey, 4, IV, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		System.arraycopy(tempkey, 4, IV, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		System.arraycopy(tempkey, 4, IV, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
		System.arraycopy(tempkey, 4, IV, 12, 4);

		// AES128 ��ȣȭ ���� key = key[]
		Key AES_key = new SecretKeySpec(key, "AES");
		Cipher AES;
		byte[] result = null;

		try {
			AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AES.init(Cipher.DECRYPT_MODE, AES_key, new IvParameterSpec(IV));
			result = AES.doFinal(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		check_sum_Contrast = checksum(result);

		// �������**********************************************************************************
		// System.out.println("��ȣȭ ��� :");

		// String s1 = new String(result);
		// String s2 = test(check_sum);
		// String s3 = test(check_sum_Contrast);
		// String s4 = test(key);
		// String s5 = test(IV);

		// System.out.println(s1);
		// System.out.println("üũ�� : " + s2 );
		// System.out.println("������ üũ�� : " + s3 );
		// System.out.println("AES_KEY : " + s4 );
		// System.out.println("�ʱ�ȭ���� : " + s5 );
		// System.out.println("___________________________________________________________________");
		// �������
		// �׽�Ʈ��****************************************************************************

		if (!Arrays.equals(check_sum, check_sum_Contrast)) { // �ؽð� �ٸ���� (�޽����� �����Ǿ��� ���)
			return "decrypt error.";
		}

		String send = new String(result);

		return send;
	}

	private byte[] fdecrypt(byte[] b) {
		byte[] check_sum = new byte[20];
		byte[] check_sum_Contrast = new byte[20];
		byte[] s = new byte[b.length - 20];

		byte seed1[] = new byte[4];
		byte seed2[] = new byte[4];
		byte seed3[] = new byte[4];
		byte seed4[] = new byte[4];

		System.arraycopy(b, b.length - 20, check_sum, 0, 20); // üũ���� ����и�
		System.arraycopy(b, 0, s, 0, b.length - 20);

		System.arraycopy(check_sum, 0, seed1, 0, 4); // üũ��������� �õ����
		System.arraycopy(check_sum, 4, seed2, 0, 4);
		System.arraycopy(check_sum, 8, seed3, 0, 4);
		System.arraycopy(check_sum, 12, seed4, 0, 4);

		byte key[] = new byte[16];
		byte IV[] = new byte[16];
		byte tempkey[] = new byte[4];

		tempkey = MT19937(seed1); // key�� �ʱ�ȭ���� ����
		System.arraycopy(tempkey, 0, key, 0, 4);
		System.arraycopy(tempkey, 4, IV, 0, 4);
		tempkey = MT19937(seed2);
		System.arraycopy(tempkey, 0, key, 4, 4);
		System.arraycopy(tempkey, 4, IV, 4, 4);
		tempkey = MT19937(seed3);
		System.arraycopy(tempkey, 0, key, 8, 4);
		System.arraycopy(tempkey, 4, IV, 8, 4);
		tempkey = MT19937(seed4);
		System.arraycopy(tempkey, 0, key, 12, 4);
		System.arraycopy(tempkey, 4, IV, 12, 4);

		// AES128 ��ȣȭ ���� key = key[]
		Key AES_key = new SecretKeySpec(key, "AES");
		Cipher AES;
		byte[] result = null;

		try {
			AES = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AES.init(Cipher.DECRYPT_MODE, AES_key, new IvParameterSpec(IV));
			result = AES.doFinal(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		check_sum_Contrast = checksum(result);

		// �������**********************************************************************************
		// System.out.println("��ȣȭ ��� :");

		// String s1 = new String(result);
		// String s2 = test(check_sum);
		// String s3 = test(check_sum_Contrast);
		// String s4 = test(key);
		// String s5 = test(IV);

		// System.out.println(s1);
		// System.out.println("üũ�� : " + s2 );
		// System.out.println("������ üũ�� : " + s3 );
		// System.out.println("AES_KEY : " + s4 );
		// System.out.println("�ʱ�ȭ���� : " + s5 );
		// System.out.println("___________________________________________________________________");
		// �������
		// �׽�Ʈ��****************************************************************************

		if (!Arrays.equals(check_sum, check_sum_Contrast)) { // �ؽð� �ٸ���� (�޽����� �����Ǿ��� ���)
			return null;
		}

		return result;
	}

	private byte[] MT19937(byte[] seed) { // �޸��� Ʈ������ ������. unsigned���� ���� ������ MT19937���� ������� �ٸ� ���� ���� ������ ����.

		long mt[] = new long[N];
		int mti = N + 1;

		int s = btoi(seed);
		mt[0] = s & 0xFFFFFFFF;
		for (mti = 1; mti < N; mti++) {
			mt[mti] = (0x6C078965 * (mt[mti - 1] ^ (mt[mti - 1] >> 30)) + mti);
		}

		long y;
		long mag01[] = new long[2];
		mag01[0] = 0x0L;
		mag01[1] = MATRIX_A;

		if (mti >= N) {
			int kk;

			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UMASK) | (mt[kk + 1] & LMASK);
				mt[kk] = mt[kk + M] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}

			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UMASK) | (mt[kk + 1] & LMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}

			y = (mt[N - 1] & UMASK) | (mt[0] & LMASK);
			mt[N - 1] = mt[M - 1] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];

			mti = 0;
		}

		y = mt[mti++];

		y ^= (y >> 11);
		y ^= (y >> 7) & 0x9D2C6780L;
		y ^= (y >> 15) & 0xEFC60000L;
		y ^= (y >> 18);

		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong(y);
		byte[] result = buf.array();

		return (byte[]) result;
	}

	public long MT19937_long(int seed) { // �޸��� Ʈ������ ������. unsigned���� ���� ������ MT19937���� ������� �ٸ� ���� ���� ������ ����.

		long mt[] = new long[N];
		int mti = N + 1;

		int s = seed;
		mt[0] = s & 0xFFFFFFFF;
		for (mti = 1; mti < N; mti++) {
			mt[mti] = (0x6C078965 * (mt[mti - 1] ^ (mt[mti - 1] >> 30)) + mti);
		}

		long y;
		long mag01[] = new long[2];
		mag01[0] = 0x0L;
		mag01[1] = MATRIX_A;

		if (mti >= N) {
			int kk;

			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UMASK) | (mt[kk + 1] & LMASK);
				mt[kk] = mt[kk + M] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}

			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UMASK) | (mt[kk + 1] & LMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];
			}

			y = (mt[N - 1] & UMASK) | (mt[0] & LMASK);
			mt[N - 1] = mt[M - 1] ^ (y >> 1) ^ mag01[(int) (y & 0x1L)];

			mti = 0;
		}

		y = mt[mti++];

		y ^= (y >> 11);
		y ^= (y >> 7) & 0x9D2C6780L;
		y ^= (y >> 15) & 0xEFC60000L;
		y ^= (y >> 18);

		return y;
	}

	private byte[] checksum(byte[] message_bin) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(message_bin);
			byte[] byteData = new byte[20];
			byteData = md.digest();
			return byteData;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return message_bin;
	}

	@SuppressWarnings("unused")
	public String test(byte[] s) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < s.length; i++)
			hexString.append(Integer.toHexString(0xFF & s[i]));
		String s3 = hexString.toString();
		return s3;
	}

	public int btoi(byte[] arr) {
		return (arr[0] & 0xff) << 24 | (arr[1] & 0xff) << 16 | (arr[2] & 0xff) << 8 | (arr[3] & 0xff);
	}

	public byte[] ltob(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}

	public byte[] do_encrypt(String s) {
		return this.encrypt(s);
	}

	public String do_decrypt(byte[] b) {
		return this.decrypt(b);
	}

	public byte[] do_fencrypt(byte[] b) {
		return this.fencrypt(b);
	}

	public byte[] do_fdecrypt(byte[] b) {
		return this.fdecrypt(b);
	}

}