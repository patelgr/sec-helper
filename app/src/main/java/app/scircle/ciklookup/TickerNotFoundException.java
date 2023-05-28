package app.scircle.ciklookup;

import java.security.InvalidParameterException;

public class TickerNotFoundException extends InvalidParameterException {
    TickerNotFoundException(String id) {
        super("Could not find ticker %s".formatted(id));
    }
}
