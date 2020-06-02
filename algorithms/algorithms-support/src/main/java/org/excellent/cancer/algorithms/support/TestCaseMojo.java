package org.excellent.cancer.algorithms.support;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.surefire.testset.TestListResolver;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class TestCaseMojo extends AbstractTestCaseMojo {

    @Override
    protected boolean verifyParameters() throws MojoExecutionException, MojoFailureException {
        return true;
    }

    @Override
    protected boolean hasExecutedBefore() {
        return false;
    }

    @NotNull
    @Override
    protected ClassScan scanForSourceClasses() throws MojoFailureException {
        return scanDirectories().append(scanDependencies());
    }

    private ClassScan scanDirectories() {
        return DirectoryScanners.scanFor(getSourceDirectory());
    }

    private ClassScan scanDependencies() {
        return null;
    }



    private TestListResolver


    private List<String> getIncludeList() throws MojoFailureException {
        return Collections.emptyList();
    }

    private List<String> getExcludeList() throws MojoFailureException {
        return Collections.emptyList();
    }


    // Source Parameters

    @Parameter(defaultValue = "${project.build.outputDirectory}")
    private File sourceClassesDirectory;

    @Parameter(property = "includesFile")
    private File includesFile;

    @Parameter(property = "excludesFile")
    private File excludesFile;

    @Override
    public File getSourceDirectory() {
        return sourceClassesDirectory;
    }

    @Override
    public File getIncludesFile() {
        return includesFile;
    }

    @Override
    public File getExcludesFile() {
        return excludesFile;
    }
}
