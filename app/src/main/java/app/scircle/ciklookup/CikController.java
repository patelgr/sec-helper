package app.scircle.ciklookup;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CikController {

    private final List<Cik> cikList = new ArrayList<>();

    @GetMapping("cik")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cik>> getCik(@NotNull @RequestParam final String[] tickers) throws IOException {
        boolean cikNeedsUpdate = true;
        final List<String> tickerList = List.of(tickers);
        if(cikList.isEmpty()){
            updateCikLookUp();
            cikNeedsUpdate = false;
        }
        List<Cik> ciks = cikList.stream()
                .filter(cikItem -> tickerList.contains(cikItem.ticker))
                .toList();

        if (ciks.isEmpty() && cikNeedsUpdate) {
            updateCikLookUp();
            ciks = cikList.stream()
                    .filter(cikItem -> tickerList.contains(cikItem.ticker))
                    .toList();
        }
        if(ciks.size() != tickerList.size() || ciks.isEmpty()){
            List<String> differences = new ArrayList<>(tickerList);
            differences.removeAll(ciks.stream().map(Cik::ticker).toList());
            throw new TickerNotFoundException(differences.toString());
        }
        return ResponseEntity.ok(ciks);
    }

    private void updateCikLookUp() throws IOException {
        HashMap<Integer, Cik> data = new ObjectMapper().readValue(
                new URL("https://www.sec.gov/files/company_tickers.json"),
                new TypeReference<>() {});
        cikList.clear();
        cikList.addAll(data.values().stream().toList());
    }

    private record Cik(@JsonAlias("cik_str") int cik, String ticker, String title) { }
}

