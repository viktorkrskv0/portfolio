package hw3;

public class StringUtils {

	public static String sortStringWords(String str) {
		// input: string with words and spaces
		// output: new lexicographic sorted string as specified

		String[] words = str.split(" ");

		boolean flag = true;
		String temp;

		while (flag) {
			flag = false;
			for (int i = 0; i < words.length - 1; i++) {
				if (words[i + 1].compareTo(words[i]) > 0) {
					temp = words[i];
					words[i] = words[i + 1];
					words[i + 1] = temp;
					flag = true;
				}
			}
		}

		String newStr = words[0];
		int i;
		for (i = 1; i < words.length - 1; i++) {
			newStr = newStr + " " + words[i];
		}
		newStr = newStr + " " + words[i];

		return newStr;
	}

	public static String deleteSubString(String sub, String s) {
		//input: string and a sub string
		//output: string without the substring
		String newStr = "";
		if (s.contains(sub)) {
			newStr = s.replace(sub, "");
		} else {
			newStr = s;
		}
		return newStr;

	}
	
	public static String mergeStrings(String a, String b){
		String str ="";
		
		for(int i=0; i<a.length();i++){
			char c1 = a.charAt(i);
			for(int j=0; j<b.length();j++){
				if(c1 == b.charAt(j)){
					str = str + c1;
				}
			}
		}
		
		return str;
	}
	
	

}
