package ca.mironov.utils.logmerge;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class LogParserTest {

    @Test
    void testParseTimestamp() {
        assertThat(LogParser.parseTimestamp("2022-03-26T19:15:43.891 DEBUG [XNIO-1 task-2] u.c.m.c.s.u.h.TokenAuthenticationHandler - Valid authorization system token requested and detected"),
                equalTo(Optional.of(Instant.parse("2022-03-26T19:15:43.891Z"))));
    }

}
