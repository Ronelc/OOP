/**
 * This class represents a book, which has a title, author, year of publication
 * and different literary aspects
 */
class Book {
	final String title;
	final String author;
	final int year;
	int comic;
	int dramatic;
	int educational;
	int currentBorrowerId = -1;

	Book(String bookTitle, String bookAuthor, int bookYearOfPublication,
		 int bookComicValue, int bookDramaticValue, int bookEducationalValue) {
		title = bookTitle;
		author = bookAuthor;
		year = bookYearOfPublication;
		comic = bookComicValue;
		dramatic = bookDramaticValue;
		educational = bookEducationalValue;
	}

	/**
	 * @return the String representation of this book.
	 */
	String stringRepresentation() {
		return "[" + title + "," + author + "," + year + "," + getLiteraryValue() + "]";
	}

	/**
	 * @return the literary value of this book, which is defined as the sum of its comic value,
	 * its dramatic value and its educational value.
	 */
	int getLiteraryValue() {return comic + dramatic + educational;}

	/**
	 *Sets the given id as the id of the current borrower of this book,
	 *  -1 if no patron is currently borrowing it.
	 */
	void setBorrowerId(int borrowerId) {currentBorrowerId = borrowerId;}


	/**
	 * the id of the current borrower of this book.
	 */
	int getCurrentBorrowerId() {return currentBorrowerId;}

	/**
	 * Marks this book as returned.
	 */
	void returnBook() {currentBorrowerId = -1;}
}
