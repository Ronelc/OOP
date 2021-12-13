/**
 * This class represents a library patron that has a name and assigns values to different
 * literary aspects of books.
 */
public class Patron {
	final String firstName;
	final String lastName;
	int comic;
	int dramatic;
	int educational;
	int enjoy;
	int amountOfBooks = 0;


	Patron(String patronFirstName, String patronLastName, int comicTendency,
		   int dramaticTendency, int educationalTendency, int patronEnjoymentThreshold) {
		firstName = patronFirstName;
		lastName = patronLastName;
		comic = comicTendency;
		dramatic = dramaticTendency;
		educational = educationalTendency;
		enjoy = patronEnjoymentThreshold;
	}

	/**
	 * @return the String representation of this patron.
	 */
	String stringRepresentation() { return firstName + " " + lastName;}

	/**
	 * @param book  - The book to asses.
	 * @return the literary value this patron assigns to the given book.
	 */
	int getBookScore(Book book) {
		return book.comic * comic + book.dramatic * dramatic + book.educational * educational;
	}

	/**
	 * @param book - The book to asses.
	 * @return true of this patron will enjoy the given book, false otherwise.
	 */
	boolean willEnjoyBook(Book book) {
		if (getBookScore(book) > enjoy) {
			return true;
		} else {
			return false;
		}
	}


}
