package org.library.library_backend.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.library_backend.Model.Book;
import org.library.library_backend.Model.Checkout;
import org.library.library_backend.Model.CheckoutStatus;
import org.library.library_backend.Model.Member;
import org.library.library_backend.Repo.BookRepo;
import org.library.library_backend.Repo.CheckoutRepo;
import org.library.library_backend.Repo.MemberRepo;
import org.library.library_backend.Service.CheckoutServiceImplementation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CheckoutServiceImplementationTest {

    @Mock
    private CheckoutRepo checkoutRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private CheckoutServiceImplementation checkoutService;

    private Book book;
    private Member member;
    private Checkout checkout;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setBook_id(1L);
        book.setTitle("Test Book");
        book.setCopies(5);

        member = new Member();
        member.setMemberId(1L);
        member.setMemberName("Test Member");

        checkout = new Checkout();
        checkout.setCheckoutId(1L);
        checkout.setBook(book);
        checkout.setMember(member);
        checkout.setStatus(CheckoutStatus.BORROWED);
        checkout.setBorrowedOn(LocalDate.now());
        checkout.setDueDate(LocalDate.now().plusWeeks(2));
    }

    // Test case for successful book checkout
    @Test
    public void testCheckoutBookSuccess() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepo.findById(1L)).thenReturn(Optional.of(member));
        when(checkoutRepo.save(any(Checkout.class))).thenReturn(checkout);

        ResponseEntity<Checkout> response = checkoutService.checkoutBook(1L, 1L, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(checkout, response.getBody());
        verify(bookRepo, times(1)).save(book);
    }

    // Test case for book not found
    @Test
    public void testCheckoutBookNotFound() {
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Checkout> response = checkoutService.checkoutBook(1L, 1L, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test case for member not found
    @Test
    public void testCheckoutMemberNotFound() {
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Checkout> response = checkoutService.checkoutBook(1L, 1L, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test case for no copies of book available
    @Test
    public void testCheckoutNoCopiesAvailable() {
        book.setCopies(0);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepo.findById(1L)).thenReturn(Optional.of(member));

        ResponseEntity<Checkout> response = checkoutService.checkoutBook(1L, 1L, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Test case for returning a book
    @Test
    public void testReturnBookSuccess() {

        checkout.setStatus(CheckoutStatus.BORROWED);

        when(checkoutRepo.findById(1L)).thenReturn(Optional.of(checkout));

        book.setCopies(5);
        when(bookRepo.save(book)).thenReturn(book);

        ResponseEntity<Checkout> response = checkoutService.returnBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CheckoutStatus.RETURNED, checkout.getStatus());
        assertEquals(6, book.getCopies());
        verify(bookRepo, times(1)).save(book);
    }

    // Test case for active checkouts retrieval
    @Test
    public void testGetActiveCheckouts() {
        when(checkoutRepo.findAllByStatus(CheckoutStatus.BORROWED)).thenReturn(Arrays.asList(checkout));

        ResponseEntity<List<Checkout>> response = checkoutService.getActiveCheckouts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // Test case for no active checkouts
    @Test
    public void testGetActiveCheckoutsNoContent() {
        when(checkoutRepo.findAllByStatus(CheckoutStatus.BORROWED)).thenReturn(Arrays.asList());

        ResponseEntity<List<Checkout>> response = checkoutService.getActiveCheckouts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test case for overdue checkouts retrieval
    @Test
    public void testGetOverdueCheckouts() {
        when(checkoutRepo.findOverdueCheckouts(LocalDate.now())).thenReturn(Arrays.asList(checkout));

        ResponseEntity<List<Checkout>> response = checkoutService.getOverdueCheckouts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}