package nl.pluralsight.stagepass.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.pluralsight.stagepass.exception.InsufficientSeatsException;
import nl.pluralsight.stagepass.model.Booking;
import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.repository.BookingRepository;
import nl.pluralsight.stagepass.repository.ConcertRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ConcertRepository concertRepository;

    public BookingService(BookingRepository bookingRepository, ConcertRepository concertRepository) {
        this.bookingRepository = bookingRepository;
        this.concertRepository = concertRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByConcert(Long concertId) {
        return bookingRepository.findByConcertId(concertId);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        Concert concert = concertRepository.findById(booking.getConcert().getId())
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        // Compute total price
        BigDecimal price = BigDecimal.valueOf(booking.getNumberOfTickets()).multiply(concert.getTicketPrice());
        booking.setTotalPrice(price);

        // Set booking date and concert reference
        booking.setBookingDate(LocalDate.now());
        booking.setConcert(concert);

        if(concert.getAvailableSeats() < booking.getNumberOfTickets()) {
            throw new InsufficientSeatsException("This concert is sold out and don't have enough seats available.");
        } 

        concert.setAvailableSeats(concert.getAvailableSeats() - booking.getNumberOfTickets());
            

       

        return bookingRepository.save(booking);
    }

    public boolean cancelBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            // Concert concert = concertRepository.findById(id)
            //     .orElseThrow(() -> new RuntimeException("Concert not found"));

            // concert.setAvailableSeats(concert.getAvailableSeats() + );
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
