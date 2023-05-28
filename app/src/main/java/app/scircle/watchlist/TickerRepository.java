package app.scircle.watchlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TickerRepository extends JpaRepository<Ticker, Long> {

    List<Ticker> findBySymbol(String symbol);
    void deleteBySymbol(String symbol);
}
