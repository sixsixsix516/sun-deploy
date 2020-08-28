package com.sixsixsix;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author SUN
 */
@Mojo(name = "deploy", defaultPhase = LifecyclePhase.PACKAGE)
public class SunDeploy extends AbstractMojo {


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hello World ! sdeploy");
    }
}
