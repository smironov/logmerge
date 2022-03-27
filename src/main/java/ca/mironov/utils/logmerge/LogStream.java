package ca.mironov.utils.logmerge;

import java.io.*;
import java.util.Optional;

class LogStream implements AutoCloseable {

    private final String name;
    private final BufferedReader reader;
    private String line;

    LogStream(String name, BufferedReader reader) throws IOException {
        this.name = name;
        this.reader = reader;
        nextLine();
    }

    String getName() {
        return name;
    }

    Optional<String> getCurrentLine() {
        return Optional.ofNullable(line);
    }

    final void nextLine() throws IOException {
        line = reader.readLine();
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
