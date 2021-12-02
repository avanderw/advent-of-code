package net.avdw.adventofcode;

import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

public abstract class Day {
    final String iso() {
        String day = this.getClass().getSimpleName().substring(3, 5);
        String year = this.getClass().getPackageName().substring(this.getClass().getPackageName().length() - 4);
        return String.format("%s-12-%s", year, day);
    }

    @Getter(lazy = true) private final String input = input();

    @SneakyThrows
    private String input() {
        URL inputUrl = this.getClass().getResource(this.getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".txt");
        return Files.readString(Paths.get(Objects.requireNonNull(inputUrl).toURI()));
    }

    abstract public String part01();

    abstract public String part02();
}
