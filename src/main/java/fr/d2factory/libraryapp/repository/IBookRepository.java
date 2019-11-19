package fr.d2factory.libraryapp.repository;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;

/**
 * The book repository Interface emulates a database via 2 HashMaps
 */
public interface IBookRepository {

	/**
	 * Add a Book to a map and delete book from borrowedbook list
	 * @param books
	 */
    public void addBooks(List<Book> books);
    /**
     * verify if the book exist or not with its ISBN
     * @param isbnCode
     * @return
     */
    public Book findBook(long isbnCode);
    /**
     * Save Borrowed Book with the date of borrow and delete book from availableBooks list
     * @param book
     * @param borrowedAt
     */
    public void saveBookBorrow(Book book, LocalDate borrowedAt);
    /**
     * Find the date when the Book was borrowed
     * @param book
     * @return
     */
    public LocalDate findBorrowedBookDate(Book book);
    
    /**
     * Delete  Book when its get borrowed
     * @param book
     */
    public void deleteBookAvailable(long isbnCode);
    
    /**
     * Delete  Book when its available
     * @param book
     */
    public void deleteBookBorrowed(Book book);
}
