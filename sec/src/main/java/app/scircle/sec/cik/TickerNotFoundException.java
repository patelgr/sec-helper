package app.scircle.sec.cik;

import java.util.Arrays;

class TickerNotFoundException extends RuntimeException {
  private final Object[] params;

  public TickerNotFoundException(final Object... params) {
    super(Arrays.toString(params));
    this.params = params;
  }

  private TickerNotFoundException(final String message, final Object... params) {
    super(message);
    this.params = params;
  }

  private TickerNotFoundException(
      final String message, final Throwable cause, final Object... params) {
    super(message, cause);
    this.params = params;
  }

  private TickerNotFoundException(final Throwable cause, final Object... params) {
    super(cause);
    this.params = params;
  }

  private TickerNotFoundException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace,
      final Object... params) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.params = params;
  }
}
