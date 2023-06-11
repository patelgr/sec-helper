package app.scircle.sec.cik;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CentralIndexKeyLookUp {
  private static final Logger log = LoggerFactory.getLogger(CentralIndexKeyLookUp.class);
  private static final List<CentralIndexKeys> CENTRAL_INDEX_KEYS_LIST = new ArrayList<>();

  public static List<CentralIndexKeys> getCikList(String[] tickers) {
    Objects.requireNonNull(tickers);
    if (tickers.length > 0) {
      log.trace("Tickers:" + Arrays.toString(tickers));
      final List<String> tickerList = List.of(tickers);
      final boolean isAllTickerPresent = new HashSet<>(
              CENTRAL_INDEX_KEYS_LIST.stream().map(CentralIndexKeys::ticker).toList())
          .containsAll(tickerList);
      final boolean uptoDateList = isAllTickerPresent || updateCikLookUp();
      log.trace("uptoDateList:" + uptoDateList);
      List<CentralIndexKeys> centralIndexKeys = CENTRAL_INDEX_KEYS_LIST.stream()
          .filter(centralIndexKeysItem -> tickerList.contains(centralIndexKeysItem.ticker()))
          .toList();
      if (centralIndexKeys.size() != tickerList.size()) {
        List<String> differences = new ArrayList<>(tickerList);
        differences.removeAll(
            centralIndexKeys.stream().map(CentralIndexKeys::ticker).toList());
        throw new TickerNotFoundException(differences);
      }
      return centralIndexKeys;
    } else {
      return Collections.emptyList();
    }
  }

  public static CentralIndexKeys getCik(String ticker) {
    Objects.requireNonNull(ticker);
    try {
      List<CentralIndexKeys> centralIndexKeys =
          getCikList(Collections.singleton(ticker).toArray(new String[0]));
      if (centralIndexKeys.size() == 1) {
        return centralIndexKeys.get(0);
      }
    } catch (TickerNotFoundException tickerNotFoundException) {
      throw new TickerNotFoundException(ticker);
    }
    throw new TickerNotFoundException(ticker);
  }

  public static boolean updateCikLookUp() {
    log.trace("Calling updateCikLookup");
    final URL secUrl;
    try {
      secUrl = new URL("https://www.sec.gov/files/company_tickers.json");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    HashMap<Integer, CentralIndexKeys> data = null;
    try {
      data = new ObjectMapper().readValue(secUrl, new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    CENTRAL_INDEX_KEYS_LIST.clear();
    CENTRAL_INDEX_KEYS_LIST.addAll(data.values().stream().toList());
    return true;
  }
}
