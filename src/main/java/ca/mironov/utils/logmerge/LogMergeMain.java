package ca.mironov.utils.logmerge;

import ca.mironov.utils.logmerge.util.LambdaUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

public final class LogMergeMain {

    public static void main(String[] args) throws IOException {
        List<LogStream> logStreams = IntStream.range(0, args.length)
                .mapToObj(i -> Map.entry(String.valueOf(i), args[i]))
                .map(entry -> LambdaUtils.rethrow(() ->
                        new LogStream(entry.getKey(), Files.newBufferedReader(Path.of(entry.getValue())))))
                .toList();
        while (logStreams.stream().anyMatch(logStream -> logStream.getCurrentLine().isPresent())) {
            LogMerger.findEarliestLine(logStreams).ifPresent(logEntry ->
                    System.out.println(logEntry.name() + ": " + logEntry.line()));
        }
        logStreams.forEach(LogStream::close);
    }

}
