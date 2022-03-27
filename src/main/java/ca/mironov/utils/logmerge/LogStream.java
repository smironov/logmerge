package ca.mironov.utils.logmerge;

import java.io.*;
import java.util.Optional;

class LogStream {

    private final String name;
    @SuppressWarnings("FieldNotUsedInToString")
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

    @SuppressWarnings("FinalMethod")
    final void nextLine() throws IOException {
        line = reader.readLine();
    }

}
