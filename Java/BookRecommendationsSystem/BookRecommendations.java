package il.ac.tau.cs.sw1.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class BookRecommendations {

	private static final int NO_RATING = -1;
	private static final int AGE_GROUP_MARGINE_SIZE = 3;

	Book[] books;
	User[] users;
	int[][] ratings;

	public BookRecommendations(Book[] books, User[] users, int[][] ratings) {
		this.books = books;
		this.users = users;
		this.ratings = ratings;
	}

	public static int[][] loadRatingsData(String fileName, User[] usersArray, Book[] booksArray) throws Exception {
		// input:csv file with book rating, array of users, and array of books
		// output: table with rating
		int n = usersArray.length;
		int m = booksArray.length;
		int[][] rating = new int[n][m];

		for (int i = 0; i < n; i++) {// initialize the table to be all -1
			for (int j = 0; j < m; j++) {
				rating[i][j] = NO_RATING;
			}
		}

		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line;
		line = bufferedReader.readLine();// don't want to read the first line as
											// it is just the header;
		int i, j;
		while ((line = bufferedReader.readLine()) != null) {
			String[] info = line.split(";");
			int userID = Integer.parseInt(info[0].substring(1, info[0].length() - 1));
			String ISBN = info[1].substring(1, info[1].length() - 1);
			i = findUserInArrayById(usersArray, userID);
			j = findBookInArrayByISBN(booksArray, ISBN);
			rating[i][j] = Integer.parseInt(info[2].substring(1, info[2].length() - 1));
		}

		bufferedReader.close();

		return rating;
	}

	public static int findUserInArrayById(User[] usersArray, int userID) {
		// input: array of users, and a user ID
		// output: the place in the array where the user with the ID is placed
		int i = 0;
		for (i = 0; i < usersArray.length; i++) {
			if (usersArray[i].getUserID() == userID) {
				break;
			}
		}
		return i;
	}

	public static int findBookInArrayByISBN(Book[] booksArray, String ISBN) {
		// input: array of books, and a ISBN
		// output: the place in the array where the book with the ISBN is placed
		int j = 0;
		for (j = 0; j < booksArray.length; j++) {
			if (booksArray[j].getISBN().equals(ISBN)) {
				break;
			}
		}
		return j;
	}

	public double getAverageRatingForUser(int userIndex) {
		// input: user index
		// output: the average of rating he did
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < this.ratings[userIndex].length; i++) {
			if (this.ratings[userIndex][i] != NO_RATING) {
				avg = avg + this.ratings[userIndex][i];
				count++;
			}
		}
		if (count == 0) {
			count++;
		}
		avg = avg / count;
		return avg;
	}

	public double getAverageRatingForBook(int bookIndex) {
		// input: book index
		// output: the average of rating he got
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < this.ratings.length; i++) {
			if (this.ratings[i][bookIndex] != NO_RATING) {
				avg = avg + this.ratings[i][bookIndex];
				count++;
			}
		}
		if (count == 0) {
			count++;
		}
		avg = avg / count;
		if (avg == 0.0) {
			avg = NO_RATING;
		}
		return avg;
	}

	public boolean[] getUsersInAgeGroup(User user) {
		// input: user
		// output: boolean array with true if the user in place is in age group.
		// false otherwise

		boolean[] isInAgeGroup = new boolean[this.users.length];
		int userAge = user.getAge();
		for (int i = 0; i < this.users.length; i++) {
			if (!users[i].hasAge()) {
				isInAgeGroup[i] = false;
			} else {
				if (Math.abs(userAge - users[i].getAge()) <= AGE_GROUP_MARGINE_SIZE) {
					isInAgeGroup[i] = true;
				} else {
					isInAgeGroup[i] = false;
				}
			}
		}
		return isInAgeGroup;
	}

	public double getAverageRatingForBookInAgeGroup(int bookIndex, boolean[] ageGroup) {
		// input: ageGroup Array and book index
		// output: average rating in the age group
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < this.ratings.length; i++) {
			if (ageGroup[i]) {
				if (ratings[i][bookIndex] != NO_RATING) {
					avg = avg + ratings[i][bookIndex];
					count++;
				}
			}
		}

		if (count == 0) {
			count++;
		}

		avg = avg / count;
		if (avg == 0.0) {
			avg = NO_RATING;
		}
		return avg;
	}

	public Book getHighestRatedBookInAgeGroup(User user) {
		//input: user
		//output: highest rated book in age group
		boolean[] ageGroup = getUsersInAgeGroup(user);
		double max = getAverageRatingForBookInAgeGroup(0,ageGroup);
		int bookIndex = 0;
		double temp;
		for (int j = 1; j < this.ratings[0].length; j++) {
			temp = getAverageRatingForBookInAgeGroup(j,ageGroup);
			if(max<temp){
				bookIndex = j;
				max = temp;
			}
		}
		return books[bookIndex];
	}

	public void printRecommendationToFile(User user, String fileName) throws Exception{
		File file = new File(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		Book book = getHighestRatedBookInAgeGroup(user);
		int bookIndex = findBookInArrayByISBN(this.books,book.getISBN());
		boolean[] ageGroup = getUsersInAgeGroup(user);
		double avgInAgeGroup = getAverageRatingForBookInAgeGroup(bookIndex,ageGroup);
		double avgInGeneral = getAverageRatingForBook(bookIndex);
		bufferedWriter.write(getRecommendedBookString(book)+"\n"+getRecommendedBookAverageInUserGroup(avgInAgeGroup)+"\n"+getRecommendedBookAverageFoAllUsers(avgInGeneral));
		
		bufferedWriter.close();
	}
	
	private String getRecommendedBookString(Book b){
		return "The recommended Book for you is: " + b.toString();
	}
	
	private String getRecommendedBookAverageInUserGroup(double average){
		return String.format("The book's average rating among its age group is: %.2f",average);
	}
	
	private String getRecommendedBookAverageFoAllUsers(double average){
		return String.format("The book's average rating among all the users is: %.2f",average);
	}

}
