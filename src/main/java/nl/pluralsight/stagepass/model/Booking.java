package nl.pluralsight.stagepass.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Name is required")
    private String customerName;

    @NotBlank(message="Email is required")
    @Email(message="Email is invalid")
    private String customerEmail;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Min(1)
    @Max(10)
    private int numberOfTickets;

    private BigDecimal totalPrice;

    private LocalDate bookingDate;

    public Booking() {}

    public Booking(Long id, String customerName, String customerEmail, Concert concert,
                   int numberOfTickets, BigDecimal totalPrice, LocalDate bookingDate) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.concert = concert;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }

    public int getNumberOfTickets() { return numberOfTickets; }
    public void setNumberOfTickets(int numberOfTickets) { this.numberOfTickets = numberOfTickets; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}
