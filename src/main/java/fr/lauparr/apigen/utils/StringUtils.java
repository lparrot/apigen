package fr.lauparr.apigen.utils;

public abstract class StringUtils {

	public static String toSnakeCase(String str) {
		int n = str.length();
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < n; i++) {
			// Converting space to underscor
			if (str.charAt(i) == ' ')
				result.append('_');
			else

				// If not space, convert into lower character
				result.append(Character.toLowerCase(str.charAt(i)));
		}

		return result.toString();
	}

}
