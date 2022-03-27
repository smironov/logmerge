package ca.mironov.utils.logmerge;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

class LogMerger {

    static Optional<LogEntry> findEarliestLine(List<? extends LogStream> logStreams) throws IOException {
        OptionalInt earliestIndex = OptionalInt.empty();
        Optional<Instant> earliestTs = Optional.empty();
        for (int i = 0; i < logStreams.size(); i++) {
            Optional<String> logStreamCurrentLine = logStreams.get(i).getCurrentLine();
            if (logStreamCurrentLine.isPresent()) {
                Optional<Instant> maybeTs = LogParser.parseTimestamp(logStreamCurrentLine.get());
                if (maybeTs.isPresent()) {
                    if (earliestTs.isEmpty() || maybeTs.get().isBefore(earliestTs.get())) {
                        earliestIndex = OptionalInt.of(i);
                        earliestTs = maybeTs;
                    }
                } else {
                    earliestIndex = OptionalInt.of(i);
                    break;
                }
            }
        }
        if (earliestIndex.isPresent()) {
            LogStream earliestLogStream = logStreams.get(earliestIndex.getAsInt());
            LogEntry logEntry = new LogEntry(earliestLogStream.getName(), earliestLogStream.getCurrentLine().orElseThrow());
            earliestLogStream.nextLine();
            return Optional.of(logEntry);
        } else {
            return Optional.empty();
        }
    }

}
