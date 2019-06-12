package il.ac.tau.cs.sw1.ex5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Book {

	private static final int MAX_BOOK_IN_FILE = 20000;

	String ISBN;
	String bookName;
	String bookAuthor;
	String yearOfPublication;
	String publisher;

	public Book(String ISBN, String bookName, String bookAuthor, String yearOfPUblication, String publisher) {
		this.ISBN = ISBN;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.yearOfPublication = yearOfPUblication;
		this.publisher = publisher;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getBookName() {
		return bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public String getPublisher() {
		return publisher;
	}

	public String toString() {
		StringBuffer sB = new StringBuffer();
		char sep = ',';
		sB.append(this.ISBN).append(sep).append(this.bookName).append(sep).append(this.bookAuthor);
		return sB.toString();
	}

	public static Book[] loadBooksData(String fileName) throws Exception {
		// input: csv file path
		// output: array of all the books in the file
		Book[] books = new Book[MAX_BOOK_IN_FILE];
		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;
		line = bufferedReader.readLine();// don't want to read the first line as
											// it is just the header;
		int i = 0;
		while ((line = bufferedReader.readLine()) != null && i < MAX_BOOK_IN_FILE) {
			String[] info = line.split(";");
			String ISBN = info[0].substring(1, info[0].length() - 1);
			String bookName = info[1].substring(1, info[1].length() - 1);
			String BookAuthor;
			if (info[2].length() > 1) {// i am doing that check because there is
										// something wrong with the info in the
										// csv file
				BookAuthor = info[2].substring(1, info[2].length() - 1);
			} else {
				BookAuthor = info[2].substring(1, 1);
			}
			String YearOfPublication = info[3].substring(1, info[3].length() - 1);
			String Publisher = info[4].substring(1, info[4].length() - 1);

			books[i] = new Book(ISBN, bookName, BookAuthor, YearOfPublication, Publisher);

			i++;
		}

		bufferedReader.close();

		if (i < MAX_BOOK_IN_FILE) {// in case of less than 20000 books
			Book[] temp = new Book[i];
			for (int j = 0; j < temp.length; j++) {
				temp[j] = books[j];
			}
			books = temp;
		}

		return books;
	}

}
