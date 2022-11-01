package demo.example;

import java.util.HashMap;

public class HashMapPrinciples {
	public static void main(String[] args) {
		HashMap<Key, String> map = new HashMap<>();
		map.put(new Key("abc"), "123");
		map.put(new Key("bcd"), "456");
		map.put(new Key("bcd"), "789");
		System.out.println(map.size());
	}

	static class Key {
		final String model;

		Key(String s) {
			model = s;
		}

	}

}
