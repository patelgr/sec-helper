package app.scircle.watchlist;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tickers")
@Data
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)

    private String symbol;

    public Ticker(String theSymbol) {
        symbol = theSymbol;
    }

    public Ticker() {

    }
}
