package il.ac.tau.cs.sw1.ex5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class User {

	int userID;
	String location;
	int age;

	private static final int NO_AGE = -1;
	private static final int MAX_USERS_IN_FILE = 20000;

	public User(int userID, String location, int age) {
		this.userID = userID;
		this.location = location;
		this.age = age;
	}

	public User(int userID, String location) {
		this.userID = userID;
		this.location = location;
		this.age = NO_AGE;
	}

	public int getUserID() {
		return userID;
	}

	public String getlocation() {
		return location;
	}

	public int getAge() {
		return age;
	}

	public boolean hasAge() {
		return this.age != NO_AGE;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", Locations=" + location + ", age=" + age + "]";
	}

	public static User[] loadUsersData(String fileName) throws Exception {
		// input: csv file path
		// output: array of all the users in the file
		User[] users = new User[MAX_USERS_IN_FILE];
		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;
		line = bufferedReader.readLine();// don't want to read the first line as
											// it is just the header;
		int i = 0;
		while ((line = bufferedReader.readLine()) != null && i < MAX_USERS_IN_FILE) {
			String[] info = line.split(";");
			int userID = Integer.parseInt(info[0].substring(1, info[0].length() - 1));// removing
																						// \"
																						// signs
			String locations = info[1].substring(1, info[1].length() - 1);
			String age = info[2];
			if (age.equals("NULL")) {
				users[i] = new User(userID, locations);
			} else {
				int ageInt = Integer.parseInt(age.substring(1, info[2].length() - 1));
				users[i] = new User(userID, locations, ageInt);
			}
			i++;
		}

		bufferedReader.close();

		if (i < MAX_USERS_IN_FILE) {// in case of less than 20000 users
			User[] temp = new User[i];
			for (int j = 0; j < temp.length; j++) {
				temp[j] = users[j];
			}
			users = temp;
		}

		return users;
	}

}
