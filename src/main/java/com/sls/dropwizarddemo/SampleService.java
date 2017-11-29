package com.sls.dropwizarddemo;

import com.sls.dropwizarddemo.core.Template;
import com.sls.dropwizarddemo.resources.SampleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SampleService extends Application<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }
    public void run(SampleConfiguration configuration, Environment environment) throws Exception {
        final Template template = configuration.buildTemplate();
        environment.jersey().register(new SampleResource(template));
    }
}
