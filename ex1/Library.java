/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books,
 * if a copy of the requested book is available.
 */
class Library {
	int maxBooks;
	int maxBorrowed;
	int maxPatron;
	Book[] booksList;
	Patron[] patronsList;


	Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
		maxBorrowed = maxBorrowedBooks;
		maxBooks = maxBookCapacity;
		maxPatron = maxPatronCapacity;
		booksList = new Book[maxBookCapacity];
		patronsList = new Patron[maxPatronCapacity];
	}

	/**
	 * @param book - The book to add to this library.
	 * @return a non-negative id number for the book if there was a spot and the book was successfully added,
	 * or if the book was already in the library; a negative number otherwise.
	 */
	int addBookToLibrary(Book book) {
		for (int i = 0; i < booksList.length; i++) {
			if (booksList[i] == book) {
				return i;
			} else if (booksList[i] == null) {
				booksList[i] = book;
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param bookId - The id to check.
	 * @return true if the given number is an id of some book in the library, false otherwise.
	 */
	boolean isBookIdValid(int bookId) {
		if (bookId == -1) {
			return false;
		} else if (bookId < booksList.length) {
			if (bookId >= 0) {
				if (booksList[bookId] != null) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * @param book - The book for which to find the id number.
	 * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
	 */
	int getBookId(Book book) {
		for (int i = 0; i < booksList.length; i++) {
			if (booksList[i] == book) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param bookId - The id number of the book to check.
	 * @return true if the book with the given id is available, false otherwise.
	 */
	boolean isBookAvailable(int bookId) {
		if (bookId == -1) {
			return false;
		} else if (bookId < booksList.length) {
			if (booksList[bookId] != null) {
				if (booksList[bookId].getCurrentBorrowerId() == -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param patron - The patron to register to this library.
	 * @return a non-negative id number for the patron if there was a spot and the patron was successfully
	 * registered or if the patron was already registered. a negative number otherwise.
	 */
	int registerPatronToLibrary(Patron patron) {
		for (int i = 0; i < patronsList.length; i++) {
			if (patronsList[i] == patron) {
				return i;
			} else if (patronsList[i] == null) {
				patronsList[i] = patron;
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param patronId - The id to check.
	 * @return true if the given number is an id of a patron in the library, false otherwise.
	 */
	boolean isPatronIdValid(int patronId) {
		if (patronId < patronsList.length) {
			if (patronId >= 0) {
				if (patronsList[patronId] != null) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * @param patron - The patron for which to find the id number.
	 * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
	 */
	int getPatronId(Patron patron) {
		for (int i = 0; i < patronsList.length; i++) {
			if (patronsList[i] == patron) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * @param bookId - The id number of the book to borrow.
	 * @param patronId - The id number of the patron that will borrow the book.
	 * @return true if the book was borrowed successfully, false otherwise.
	 */
	boolean borrowBook(int bookId, int patronId) {
		if (isPatronIdValid(patronId)) {
			if (isBookAvailable(bookId)) {
				if (patronsList[patronId].amountOfBooks < maxBorrowed) {
					if (patronsList[patronId].willEnjoyBook(booksList[bookId])) {
						booksList[bookId].setBorrowerId(patronId);
						patronsList[patronId].amountOfBooks += 1;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return the given book.
	 * @param bookId - The id number of the book to return.
	 */
	void returnBook(int bookId) {
		if (bookId != -1) {
			if (bookId < booksList.length) {
				int patronId;
				patronId = booksList[bookId].getCurrentBorrowerId();
				if (patronId >= 0) {
					patronsList[patronId].amountOfBooks -= 1;
					booksList[bookId].setBorrowerId(-1);
				}
			}
		}
	}


	/**
	 * @param patronId - The id number of the patron to suggest the book to.
	 * @return
	The available book the patron with the given ID will enjoy the most. Null if no book is available.
	 */
	Book suggestBookToPatron(int patronId) {
		int max = 0;
		int score = 0;
		Book book = null;
		if (isPatronIdValid(patronId)) {
			for (int i = 0; i < booksList.length; i++) {
				if (isBookAvailable(i)) {
					score = patronsList[patronId].getBookScore(booksList[i]);
					if (score > max) {
						max = score;
						book = booksList[i];
					}
				}
			}
			if (max > patronsList[patronId].enjoy) {
				return book;
			}
		}
		return null;
	}


}
