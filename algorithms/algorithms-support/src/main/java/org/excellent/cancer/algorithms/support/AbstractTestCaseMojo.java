package org.excellent.cancer.algorithms.support;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

public abstract class AbstractTestCaseMojo extends AbstractMojo implements SolutionExecutionParameters {

    private List<CommandLineOption> cli;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        cli = CommandLineOption.commandLineOptions(getLog());

        if (!verifyParameters()) {
            getLog().error("Parameter verification failed");
            return;
        }

        if (hasExecutedBefore()) {
            getLog().info("Skipping execution of test-case because it has already been for this configuration");
        }

        ClassScan classes = scanForSourceClasses();
        if (classes.isEmpty()) {
            getLog().info("No solution of Leetcode to test");
        }
    }

    protected abstract boolean verifyParameters() throws MojoExecutionException, MojoFailureException;

    protected abstract boolean hasExecutedBefore();

    protected abstract ClassScan scanForSourceClasses() throws MojoFailureException;
}
