package ca.mironov.utils.logmerge;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class LogMergeMain {

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(String[] args) throws IOException {
        List<LogStream> logStreams = IntStream.range(0, args.length).mapToObj(i -> Map.entry(String.valueOf(i), args[i])).map(entry -> rethrow(() ->
                new LogStream(entry.getKey(), Files.newBufferedReader(Path.of(entry.getValue()))))).toList();
        while (logStreams.stream().anyMatch(logStream -> logStream.getCurrentLine().isPresent())) {
            LogMerger.findEarliestLine(logStreams).ifPresent(logEntry ->
                    System.out.println(logEntry.name() + ": " + logEntry.line()));
        }
        System.exit(1);
    }


    @SuppressWarnings("ProhibitedExceptionThrown")
    private static <V> V rethrow(Callable<V> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
