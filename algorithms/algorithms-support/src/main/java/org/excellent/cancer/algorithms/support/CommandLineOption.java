package org.excellent.cancer.algorithms.support;

import org.apache.maven.plugin.logging.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CLI options.
 */
public enum CommandLineOption {
    LOGGING_LEVEL_WARN,
    LOGGING_LEVEL_INFO,
    LOGGING_LEVEL_ERROR,
    LOGGING_LEVEL_DEBUG;

    public static List<CommandLineOption> commandLineOptions(Log logger) {
        List<CommandLineOption> cli = new ArrayList<>();
        if (logger.isErrorEnabled()) {
            cli.add(LOGGING_LEVEL_ERROR);
        }
        if (logger.isWarnEnabled()) {
            cli.add(LOGGING_LEVEL_WARN);
        }
        if (logger.isInfoEnabled()) {
            cli.add(LOGGING_LEVEL_INFO);
        }
        if (logger.isDebugEnabled()) {
            cli.add(LOGGING_LEVEL_DEBUG);
        }

        return Collections.unmodifiableList(cli);
    }
}
