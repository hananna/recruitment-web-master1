package fr.d2factory.libraryapp.repositoryImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.repository.IBookRepository;

/**
 * The book repository implementation  emulates a database via 2 HashMaps
 */
public class BookRepositoryImpl implements IBookRepository{
	
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
    	books.forEach(book -> availableBooks.put(book.getIsbn(), book)); 	
    }

    public Book findBook(long isbnCode) {  	
		return availableBooks.entrySet().stream()
			    .filter(entry -> entry.getKey().getIsbnCode() == isbnCode)
			    .findFirst()
			    .map(Map.Entry::getValue)
			    .orElse(null);

    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
       if(book != null && book.getIsbn() != null) {
    	   if(findBook(book.getIsbn().getIsbnCode()) != null) {
    		borrowedBooks.put(book, borrowedAt);
    	   }
       }
    }

    public LocalDate findBorrowedBookDate(Book book) {
		return borrowedBooks.entrySet().stream()
			    .filter(entry -> entry.getKey().equals(book))
			    .findFirst()
			    .map(Map.Entry::getValue)
			    .orElse(null);

    }
    @Override
   public  void deleteBookAvailable(long isbnCode){  	
    	Book bookTobeDelete = findBook(isbnCode);
		if(bookTobeDelete != null) {
			availableBooks.remove(bookTobeDelete.getIsbn());
		}

     }
    
    @Override
	public void deleteBookBorrowed(Book book) {
		if( findBorrowedBookDate(book) != null) {
			borrowedBooks.remove(book);
		}

		
	}
    
	public Map<ISBN, Book> getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(Map<ISBN, Book> availableBooks) {
		this.availableBooks = availableBooks;
	}

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	
    
    
}
