package com.sls.dropwizarddemo;

import com.sls.dropwizarddemo.core.Template;
import io.dropwizard.Configuration;

public class SampleConfiguration extends Configuration{

    private String template;
    private String defaultName = "Stranger";

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }
}
