package fr.d2factory.libraryapp.libraryServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import dr.d2factory.libraryapp.constante.LibraryConstants;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.libraryException.HasLateBooksException;
import fr.d2factory.libraryapp.libraryService.ILibraryService;
import fr.d2factory.libraryapp.member.Student;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.repository.IBookRepository;

public class LibraryServiceImpl implements ILibraryService {

	/** The book repository. */
	private IBookRepository bookRepository;

	public LibraryServiceImpl(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book borrowBook(final long isbnCode, Member member, final LocalDate borrowedAt) throws HasLateBooksException {
		if (member != null) {
			if (hasbookLate(member)) {
				throw new HasLateBooksException(" \"en retard\" ");
			} else {
				// verify if the book is available or not
				Book BookToBorrow = bookRepository.findBook(isbnCode);
				if (BookToBorrow != null) {
					bookRepository.saveBookBorrow(BookToBorrow, borrowedAt);
					bookRepository.deleteBookAvailable(isbnCode);
					if(member.getBooksBorrowed() != null) {
						member.getBooksBorrowed().add(BookToBorrow);	
					}else {
						member.setBooksBorrowed(Arrays.asList(BookToBorrow));
					}
					return BookToBorrow;
				}

			}
		}
		return null;
	}

	@Override
	public void returnBook(Book book, Member member) {
		bookRepository.addBooks(Arrays.asList(book));
		bookRepository.deleteBookBorrowed(book);
		member.getBooksBorrowed().remove(book);					

	}

	public boolean hasbookLate(final Member member) {
		LocalDate today = LocalDate.now();	
		boolean hasbookLate = false;
		int amountOfDaysAllowed = 0;
		
		if (member instanceof Student) {
			amountOfDaysAllowed = LibraryConstants.DAYS_BEFORE_LATE_STUDENT;
		}
		if (member instanceof Resident) {
			amountOfDaysAllowed = LibraryConstants.DAYS_BEFORE_LATE_RESIDENT;
		}

		List<Book> booksBorrowed = member.getBooksBorrowed();
		if(booksBorrowed != null) {
		for (Book bookBorrowed : booksBorrowed) {
			if (bookRepository.findBorrowedBookDate(bookBorrowed).plusDays(amountOfDaysAllowed).isBefore(today)) {
				hasbookLate = true;
			}
		}
		}
		return hasbookLate;

	}
}
