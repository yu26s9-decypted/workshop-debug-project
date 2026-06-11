package nl.pluralsight.stagepass.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.pluralsight.stagepass.model.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByArtistId(Long artistId);
    List<Concert> findByDateAfterOrderByDateAsc(LocalDate date);
}


