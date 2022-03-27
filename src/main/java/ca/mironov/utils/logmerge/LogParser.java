package ca.mironov.utils.logmerge;

import java.time.Instant;
import java.util.Optional;
import java.util.regex.*;

class LogParser {

    private static final Pattern TS1_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{2}:\\d{2}.\\d{3}) .+");
    private static final Pattern TS2_PATTERN = Pattern.compile("(\\d{1,2}:\\d{2}:\\d{2},\\d{3}) .+");

    static Optional<Instant> parseTimestamp(String line) {
        Matcher m1 = TS1_PATTERN.matcher(line);
        if (m1.matches())
            return Optional.of(Instant.parse(m1.group(1) + 'Z'));
        Matcher m2 = TS2_PATTERN.matcher(line);
        if (m2.matches())
            return Optional.empty();
        if (!line.isEmpty() && Character.isDigit(line.charAt(0)))
            throw new IllegalArgumentException("Unsupported timestamp format: " + line);
        return Optional.empty();
    }

}
