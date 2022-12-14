import java.text.SimpleDateFormat;

public class idcode_gen {
	public long make_member_code(String id, String nickname) {
		SimpleDateFormat base_date_format = new SimpleDateFormat("yyyyMMddHHMM");
		String base_time = base_date_format.format(System.currentTimeMillis());
		String key = id + nickname + base_time;
		int a = 0;
		for (int i = 0; i < key.length(); i++) {
			a = a << 2;
			a += key.charAt(i) * i;
			a = a >> 1;
			a += key.charAt(i);
		}
		String_crypto crypt = new String_crypto();
		long result = crypt.MT19937_long(a);
		return result;
	}

	public long make_room_code(String roomname, String maker_id) {
		SimpleDateFormat base_date_format = new SimpleDateFormat("yyyyMMddHHMM");
		String base_time = base_date_format.format(System.currentTimeMillis());
		String key = roomname + maker_id + base_time;
		int a = 0;
		for (int i = 0; i < key.length(); i++) {
			a = a << 2;
			a += key.charAt(i) * i;
			a = a >> 1;
			a += key.charAt(i);
		}
		String_crypto crypt = new String_crypto();
		long result = crypt.MT19937_long(a);
		return result;
	}

	public static void main(String[] args) {
		idcode_gen id = new idcode_gen();
		System.out.println(id.make_member_code("asdasd", "asdsadsa"));
		System.out.println(id.make_member_code("asdasd", "asdsadsa"));
		System.out.println(id.make_member_code("asdasd", "asdsada"));
	}
}