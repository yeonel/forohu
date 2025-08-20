package com.alura.forohub.infrastructure.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyLog {

    // ANSI escape codes for formatting
    private static final String ANSI_CYAN = "\033[0;36m"; // Cyan
    private static final String ANSI_BOLD_RED = "\033[1;31m"; // Bold Red
    private static final String ANSI_RESET = "\033[0m"; // Reset formatting

    /**
     * Logs an info message in cyan.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        log.info(ANSI_CYAN + message + ANSI_RESET);
    }

    /**
     * Logs an error message in bold red.
     *
     * @param message The message to log.
     */
    public static void error(String message) {
        log.error(ANSI_BOLD_RED + message + ANSI_RESET);
    }
}