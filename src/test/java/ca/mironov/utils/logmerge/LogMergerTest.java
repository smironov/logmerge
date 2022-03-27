package ca.mironov.utils.logmerge;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

class LogMergerTest {

    @Test
    void test() throws IOException {
        LogStream logStream1 = new LogStream("1", new BufferedReader(new StringReader(
                "year 1999")));
        LogStream logStream2 = new LogStream("2", new BufferedReader(new StringReader(
                "2001-01-01T00:00:00.000 year 2001")));
        LogStream logStream3 = new LogStream("3", new BufferedReader(new StringReader(
                "2000-01-01T00:00:00.000 year 2000")));
        List<LogStream> logStreams = List.of(logStream1, logStream2, logStream3);
        assertThat(LogMerger.findEarliestLine(logStreams).get().line(), Matchers.equalTo("2000-01-01T00:00:00.000 year 2000"));
        assertThat(LogMerger.findEarliestLine(logStreams).get().line(), Matchers.equalTo("2001-01-01T00:00:00.000 year 2001"));
        assertThat(LogMerger.findEarliestLine(logStreams).get().line(), Matchers.equalTo("year 1999"));
        assertThat(LogMerger.findEarliestLine(logStreams), Matchers.equalTo(Optional.empty()));
    }

}
