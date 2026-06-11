package nl.pluralsight.stagepass.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.model.ConcertSummary;
import nl.pluralsight.stagepass.service.BookingService;
import nl.pluralsight.stagepass.service.ConcertService;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;
    private final BookingService bookingService;

    public ConcertController(ConcertService concertService, BookingService bookingService) {
        this.concertService = concertService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        return ResponseEntity.ok(concertService.getAllConcerts());
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Concert>> getConcertByArtist(@PathVariable Long artistId){
        return ResponseEntity.ok(concertService.getConcertsByArtist(artistId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Concert>> getUpcomingConcert(){
        return ResponseEntity.ok(concertService.getUpcomingConcert());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return concertService.getConcertById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     @GetMapping("/{id}/summary")
     public ResponseEntity<ConcertSummary> getConcertSummary(@PathVariable Long id ){
        return ResponseEntity.ok(concertService.concertSummary(id));
     }

    

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) {
        Concert created = concertService.createConcert(concert);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concert> updateConcert(@PathVariable Long id, @RequestBody Concert concert) {
        return concertService.updateConcert(id, concert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id) {
        if (concertService.deleteConcert(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
