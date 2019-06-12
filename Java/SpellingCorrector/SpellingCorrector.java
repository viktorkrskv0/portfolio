package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SpellingCorrector {

	public static void main(String args[]) throws FileNotFoundException {
		File f = new File(args[0]);
		Scanner sc = new Scanner(f);
		String[] vocabulary = scanVocabulary(sc);
		System.out.println("Read " + vocabulary.length + " words from " + args[0]);
		outerloop: while (true) {
			System.out.println("Enter your sentence:");
			sc = new Scanner(System.in);
			String input = sc.nextLine();
			if (input.equals("quit")) {
				break outerloop;
			}
			String[] words = splitSentence(input);
			String[][] table = new String[3][];
			int wrongWordsCounter = 0;
			for (int m = 0; m < words.length; m++) {
				if (!isInVocabulary(vocabulary, words[m])) {
					wrongWordsCounter++;
					table = findSimilarWords(words[m], vocabulary);
					System.out.println("the word " + words[m] + " is incorrect");
					System.out.println(table[1].length + " corrections of distance 1");
					System.out.println(table[2].length + " corrections of distance 2");

					String[] ansArray = new String[8];
					int l = 0;
					while (l < ansArray.length && l < table[1].length) {
						ansArray[l] = table[1][l];
						l++;
					}
					while (l < ansArray.length && l - table[1].length < table[2].length) {
						ansArray[l] = table[2][l - table[1].length];
						l++;
					}
					int o = 0;
					while (o < ansArray.length && ansArray[o] != null) {
						System.out.println((o + 1) + ". " + ansArray[o]);
						o++;
					}
					System.out.println("Enter your choice: ");
					sc = new Scanner(System.in);
					while (!sc.hasNextInt()) {
						if (sc.next().equals("quit")) {
							break outerloop;
						}
						System.out.println("[WARNING] Invalid choice, try again.");
					}
					int choiceNumber = sc.nextInt();
					while (choiceNumber > 8 || choiceNumber <= 0 || ansArray[choiceNumber - 1] == null) {
						System.out.println("[WARNING] Invalid choice, try again.");
						choiceNumber = sc.nextInt();
					}
					words[m] = ansArray[choiceNumber - 1];

				}
			}
			System.out.println("The correct sentence is: " + buildSentence(words));
			System.out.println("Corrected " + wrongWordsCounter + " words.");
		}
		sc.close();
	}

	public static String[] scanVocabulary(Scanner scanner) {
		// input: scanner that is pointing to a text file
		// output: array with the strings sorted in a lexicographic manner and
		// no multiple shows
		String tempStr = "";
		while (scanner.hasNextLine()) {
			tempStr = tempStr + scanner.nextLine() + " ";
		}
		String[] words = tempStr.split(" ");
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].toLowerCase();
		}
		Arrays.sort(words);// sorting

		String[] temp = new String[words.length];

		int j = 0;
		if (!words[0].equals("")) {
			temp[0] = words[0];
			j++;
		}

		int i = 1;
		while (i < words.length) {
			if (i > 0) {
				if (!words[i].equals(words[i - 1]) && words[i] != "") {
					temp[j] = words[i];
					j++;
				}
			}
			i++;
		}

		String[] anotherTemp = new String[j];
		for (int k = 0; k < j; k++) {
			anotherTemp[k] = temp[k];
		}

		words = anotherTemp;

		String[] thirdTemp = new String[3000];
		if (anotherTemp.length > 3000) {
			for (int k = 0; k < 3000; k++) {
				thirdTemp[k] = anotherTemp[k];
			}
			words = thirdTemp;
		}

		return words;
	}

	public static int calcHammingDistance(String word1, String word2) {
		// input: two strings
		// output: the hamming distance

		int len1 = word1.length(), len2 = word2.length(), checkUntil;
		int hammingDistance = Math.abs(len1 - len2);
		if (len1 < len2) {
			checkUntil = len1;
		} else {
			checkUntil = len2;
		}
		for (int i = 0; i < checkUntil; i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				hammingDistance++;
			}
		}

		return hammingDistance;

	}

	public static String[][] findSimilarWords(String word, String[] vocabulary) {
		// input: word and an array of words
		// output: array of three arrays. as specified in the question
		String[][] table = new String[3][];
		
		table[0] = new String[0];
		if(isInVocabulary(vocabulary,word)){
			table[0] = new String[1];
			table[0][0] = word;
		}
		
		int hamming1Counter = 0, hamming2Counter = 0;
		for (String str : vocabulary) {
			if (calcHammingDistance(str, word) == 1) {
				hamming1Counter++;
			} else {
				if (calcHammingDistance(str, word) == 2) {
					hamming2Counter++;
				}
			}
		}

		table[1] = new String[hamming1Counter];
		table[2] = new String[hamming2Counter];
		hamming1Counter = 0;
		hamming2Counter = 0;
		for (String str : vocabulary) {
			if (calcHammingDistance(str, word) == 0) {
				table[0] = new String[1];
				table[0][0] = word;
			}
			if (calcHammingDistance(str, word) == 1) {
				table[1][hamming1Counter] = str;
				hamming1Counter++;
			}
			if (calcHammingDistance(str, word) == 2) {
				table[2][hamming2Counter] = str;
				hamming2Counter++;
			}
		}
		Arrays.sort(table[1]);
		Arrays.sort(table[2]);
		
		return table;
	}

	public static String[] splitSentence(String sentence) {
		// input: sentence
		// output: array with the words, not including " "
		String[] temp = sentence.split(" ");
		int counter = 0;
		for (String str : temp) {
			if (!str.equals("")) {
				counter++;
			}
		}
		String[] words = new String[counter];
		int wordCounter = 0;
		for (int i = 0; i < counter; i++) {
			while (temp[wordCounter].equals("")) {
				wordCounter++;
			}
			words[i] = temp[wordCounter].toLowerCase();
			wordCounter++;
		}
		return words;
	}

	public static String buildSentence(String[] words) {
		// input: array of words.
		// output: sentence built from the words
		String sentence = "";
		for (String str : words) {
			sentence = sentence + " " + str;
		}
		return sentence.trim();

	}

	public static boolean isInVocabulary(String[] vocabulary, String word) {
		// input: array and string
		// output: true if string in array, else false
		for (String str : vocabulary) {
			if (str.equals(word)) {
				return true;
			}
		}
		return false;
	}

}
