package app.scircle.watchlist;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TickerController {

    private final TickerRepository tickerRepository;


    public TickerController(TickerRepository tickerRepository) {
        this.tickerRepository = tickerRepository;
    }

    @GetMapping("/tickers")
    public ResponseEntity<List<Ticker>> getAllTickers() {
        List<Ticker> tickerList = new ArrayList<>(tickerRepository.findAll());

        if (tickerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(tickerList, HttpStatus.OK);
        }

    }
    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<Ticker> getByTicker(@PathVariable("ticker") String ticker) {
            List<Ticker> tickerList = tickerRepository.findBySymbol(ticker);
            return switch (tickerList.size()) {
                case 0 -> new ResponseEntity<>(HttpStatus.NO_CONTENT);
                case 1 -> new ResponseEntity<>(tickerList.get(0), HttpStatus.OK);
                default -> throw new IllegalStateException("Unexpected value: " + tickerList.size());
            };
    }
    @GetMapping("/ticker")
    public ResponseEntity<Ticker> getByTickerId(@RequestParam("id") long tickerId) {
        return tickerRepository.findById(tickerId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/ticker/{ticker}")
    public ResponseEntity<Ticker> addTicker(@PathVariable String ticker) {
        return new ResponseEntity<>(
                tickerRepository.save(new Ticker(ticker.toUpperCase().trim())),
                HttpStatus.CREATED
        );
    }
    @DeleteMapping("/ticker")
    public ResponseEntity<HttpStatus> deleteTickerById(@RequestParam("id") long id) {
        tickerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/ticker/{ticker}")
    public ResponseEntity<HttpStatus> deleteTickerByName(@PathVariable("ticker") String ticker) {
        tickerRepository.deleteBySymbol(ticker);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tickers")
    public ResponseEntity<HttpStatus> deleteAllTickers() {
        tickerRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/ticker")
    public ResponseEntity<Ticker> updateTicker(@RequestParam("id") long id, @RequestBody Ticker ticker) {
        Optional<Ticker> tickerData = tickerRepository.findById(id);
        if (tickerData.isPresent()) {
            Ticker newTicker = tickerData.get();
            newTicker.setSymbol(ticker.getSymbol());
            return new ResponseEntity<>(tickerRepository.save(newTicker), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
