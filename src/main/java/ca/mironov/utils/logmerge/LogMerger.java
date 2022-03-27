package ca.mironov.utils.logmerge;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

class LogMerger {

    static Optional<LogEntry> findEarliestLine(List<? extends LogStream> logStreams) throws IOException {
        int earliestIndex = -1;
        Instant earliestTs = null;
        for (int i = 0; i < logStreams.size(); i++) {
            LogStream logStream = logStreams.get(i);
            if (logStream.getCurrentLine().isPresent()) {
                Optional<Instant> maybeTs = LogParser.parseTimestamp(logStream.getCurrentLine().get());
                if (maybeTs.isPresent()) {
                    if (earliestTs == null || maybeTs.get().isBefore(earliestTs)) {
                        earliestIndex = i;
                        earliestTs = maybeTs.get();
                    }
                } else {
                    earliestIndex = i;
                    break;
                }
            }
        }
        if (earliestIndex != -1) {
            LogStream earliestLogStream = logStreams.get(earliestIndex);
            String earliestLine = earliestLogStream.getCurrentLine().orElseThrow();
            earliestLogStream.nextLine();
            return Optional.of(new LogEntry(earliestLogStream.getName(), earliestLine));
        } else {
            return Optional.empty();
        }
    }

}
