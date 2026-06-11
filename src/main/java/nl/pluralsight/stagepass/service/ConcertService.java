package nl.pluralsight.stagepass.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.pluralsight.stagepass.model.Booking;
import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.model.ConcertSummary;
import nl.pluralsight.stagepass.repository.ConcertRepository;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final BookingService bookingService;

    public ConcertService(ConcertRepository concertRepository, BookingService bookingService) {
        this.concertRepository = concertRepository;
        this.bookingService = bookingService;
    }

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public Optional<Concert> getConcertById(Long id) {
        return concertRepository.findById(id);
    }

    public Concert createConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public List<Concert> getConcertsByArtist(Long artistId){
        return concertRepository.findByArtistId(artistId);
    }

    public List<Concert> getUpcomingConcert(){
        LocalDate now = LocalDate.now();
        return concertRepository.findByDateAfterOrderByDateAsc(now);
    }

    public ConcertSummary concertSummary(Long id){
       Concert concert = concertRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("cocnert was not found"));

        List<Booking> bookings = bookingService.getBookingsByConcert(id);
    
        int seatBooked = bookings.stream()
            .mapToInt(Booking::getNumberOfTickets)
            .sum();

        BigDecimal totalRevenue = bookings.stream()
            .map(Booking::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ConcertSummary(id, concert.getTitle(), concert.getTotalSeats(), seatBooked, concert.getAvailableSeats(), totalRevenue);    
    }

    public Optional<Concert> updateConcert(Long id, Concert updatedConcert) {
        return concertRepository.findById(id).map(existing -> {
            existing.setTitle(updatedConcert.getTitle());
            existing.setDate(updatedConcert.getDate());
            existing.setArtist(updatedConcert.getArtist());
            existing.setVenue(updatedConcert.getVenue());
            existing.setTotalSeats(updatedConcert.getTotalSeats());
            existing.setAvailableSeats(updatedConcert.getAvailableSeats());
            existing.setTicketPrice(updatedConcert.getTicketPrice());
            return concertRepository.save(existing);
        });
    }

    public boolean deleteConcert(Long id) {
        if (concertRepository.existsById(id)) {
            concertRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
