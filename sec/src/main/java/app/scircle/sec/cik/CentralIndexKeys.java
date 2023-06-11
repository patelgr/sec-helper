package app.scircle.sec.cik;

import com.fasterxml.jackson.annotation.JsonAlias;

record CentralIndexKeys(@JsonAlias("cik_str") int cik, String ticker, String title) {}
