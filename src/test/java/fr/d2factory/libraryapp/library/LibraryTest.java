package fr.d2factory.libraryapp.library;

import org.junit.Before;
import org.junit.Test;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.libraryException.HasLateBooksException;
import fr.d2factory.libraryapp.libraryService.ILibraryService;
import fr.d2factory.libraryapp.libraryServiceImpl.LibraryServiceImpl;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import fr.d2factory.libraryapp.repositoryImpl.BookRepositoryImpl;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LibraryTest {
    private ILibraryService library ;
    private BookRepositoryImpl bookRepository;
    


    @Before
    public void setup(){
    	bookRepository = new BookRepositoryImpl();
    	library = new LibraryServiceImpl(bookRepository);
        /** The list of books. */
        List<Book> availableBooks = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("src\\test\\resources\\books.json"));
            List<Book> list = new ArrayList<>();
            for (Object o : a) {
                JSONObject book = (JSONObject) o;
                String title = (String) book.get("title");
                String author = (String) book.get("author");
                long isbn = (long) ((JSONObject) book.get("isbn")).get("isbnCode");
                Book tmp = new Book(title, author, new ISBN(isbn));
                list.add(tmp);
            }
            availableBooks = Collections.unmodifiableList(list);
        } catch (ParseException | IOException e) {
            fail("books.json not found or error while parsing it");
        }
        bookRepository.addBooks(availableBooks);
    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available(){    	
		Member studentTest = new Student(10,"1");
		Book book = library.borrowBook(46578964513l, studentTest, LocalDate.now());
		assertEquals(46578964513l, book.getIsbn().getIsbnCode());
    }
    @Test
    public void borrowed_book_is_no_longer_available(){
		Member studentTest = new Student(10,"1");
		library.borrowBook(46578964513l, studentTest, LocalDate.now());

		assertEquals(null, bookRepository.findBook(46578964513l));
    }
    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
		Member residentTest = new Resident();
		residentTest.setWallet(10);	
		residentTest.payBook(1);	
		assertEquals(9.9f, residentTest.getWallet(), 0f);
    }
    @Test
    public void students_pay_10_cents_the_first_30days(){
		Member studentTest = new Student();
		studentTest.setWallet(10);	
		studentTest.payBook(1);		
									
		assertEquals(9.9f, studentTest.getWallet(), 0f);
    }
    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days(){
    	Student studentTest = new Student();
		studentTest.setAnneeEtude("1");
		studentTest.setWallet(10);	
		studentTest.payBook(15);	
		assertEquals(10f, studentTest.getWallet(), 0f);
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days(){
    	Student studentTest = new Student();
		studentTest.setWallet(6.2500014f);	
		studentTest.payBook(45);	
		assertEquals(1f, studentTest.getWallet(), 0f);
    }
    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
		Member residentTest = new Resident();
		residentTest.setWallet(6.4f);	
		residentTest.payBook(61);	
		assertEquals(0.20000029f, residentTest.getWallet(), 0f);
    }
  	@Test(expected=HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books(){
  		Member memberTest = new Resident();
  		// Resident has a book  over than 60 days
  		library.borrowBook(46578964513l, memberTest, LocalDate.now().minusDays(61));
  		// Resident can t  to borrow a book today
  		library.borrowBook(968787565445l, memberTest, LocalDate.now());
    }
}
