Creating a Microservice
1.  Use intelliJ to create a new Maven project
2.  Follow dropwizard directions - http://www.dropwizard.io/1.2.1/docs/getting-started.html
3.  Edit the pom.xml to add dropwizard core dependencies
    <properties>
        <dropwizard.version>1.2.1</dropwizard.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-core</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>
    </dependencies>

4.  Wait for maven to dl the dropwizard dependencies
5.  Also add the GSON dependency to allow converting to and from JSON
        <!-- GSon JSON Parsing -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.0</version>
        </dependency>
    </dependencies>

6.  In the src/main/java folder create a new class
com.sls.dropwizarddemo.SampleService
7. have the new class extend Application (dropwizard one) with template SampleConfiguration
package com.sls.dropwizarddemo;
import io.dropwizard.Application;
public class SampleService extends Application<SampleConfiguration> {
}
8.  Create SampleConfiguration class next to SampleService and extend dropwizard.Configuration
package com.sls.dropwizarddemo;
import io.dropwizard.Configuration;
public class SampleConfiguration extends Configuration{
}
9.  Use option click to add run method to SampleService
package com.sls.dropwizarddemo;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
public class SampleService extends Application<SampleConfiguration> {
    public void run(SampleConfiguration configuration, Environment environment) throws Exception {
    }
}
10.  Add a main method to the SampleService by entering psvm + Tab
package com.sls.dropwizarddemo;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
public class SampleService extends Application<SampleConfiguration> {
    public static void main(String[] args) {
    }
    public void run(SampleConfiguration configuration, Environment environment) throws Exception {
    }
}
11.  Add run method call to main
new SampleService().run(args);
12.  The intelliJ complains about unhandled exception so option click on the red underline then the lightbulb and add
exception to method signature.
package com.sls.dropwizarddemo;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
public class SampleService extends Application<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }
    public void run(SampleConfiguration configuration, Environment environment) throws Exception {
    }
}
13.  Add a configuration file on the top level next to the pom.xml called sample.yml
and modify it to add the http port and admin port
server:
  applicationConnectors:
    - type: http
      port: 8080
  adminConnectors:
    - type: http
      port: 8081

14. Right click the SampleService in the Project and choose create 'SampleService.main()'
in the Program Arguments field select server sample.yml - this will tell compiler that when I run or debug it,
to pass those parameters to main.
This creates a run profile at the top with triangle and bug icons to run or debug it

15. Try running it by pressing the green run icon and then stopping it with the red square icon

16.  Create a core.Template class under dropwizarddemo
package com.sls.dropwizarddemo.core;
import java.util.Optional;
import static java.lang.String.format;
public class Template {
    private final String content;
    private final String defaultName;
    public Template(String content, String defaultName) {
        this.content = content;
        this.defaultName = defaultName;
    }
    public String render(Optional<String> name) {
        return format(content, name.orElse(defaultName));
    }
}

17.  Modify the SampleConfiguration to build the template
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

18.  Add the Template, the configuration call and a resource to SampleService
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

19.  It will complain about SampleResource so option click it and add SampleResource class
(note: change path to add resources folder to dropwizarddemo

20.  The SampleResource is where the response to the endpoint resides

package com.sls.dropwizarddemo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import com.google.gson.Gson;

@Path("/greetings/{name}")
public class SampleResource {
    public SampleResource(Object p0) {
    }
    @GET
    public String getGreetings(@PathParam("name") String name) {
        Gson gson = new Gson();
        return gson.toJson("Hello, " + name + "!");
    }
}

// notice how we convert the regular Java String to JSON using GSON

21.  Press the run icon and verify that the GET /greeting/{nam} shows up in the SampleService window at the bottom
GET     /greetings/{name} (com.sls.dropwizarddemo.resources.HelloWorldResource)

22.  Open a browser and navigate to the microservice
http://localhost:8080/greetings/Robert
verify that you get results in browser

23.  Set a breakpoint in the return statement in HelloWorldResource.java
Stop server via the red square and restart in debug mode using the bug icon at the top
refresh browser and debug into the microservice

24.  Stop it in the ide by pressing the red square stop icon

25.  If you want to be able to build a FAT jar and run it from the command line you will also need
the maven-shade-plugin in your pom.

           <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <createDependencyReducedPom>true</createDependencyReducedPom>
                    <filters>
                        <filter>
                            <artifact>*:*</artifact>
                            <excludes>
                                <exclude>META-INF/*.SF</exclude>
                                <exclude>META-INF/*.DSA</exclude>
                                <exclude>META-INF/*.RSA</exclude>
                            </excludes>
                        </filter>
                    </filters>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.sls.dropwizarddemo.SampleService</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

26.  Notice the mainClass above - Java will need this to run from the command line

27.  Test compiling it from the command line by navigating to the folder with the pom and entering
$ mvn clean complile

28.  Create a jar from the command line next to the pom
$ mvn package
Note:  You should see building jar with the jar name

29.  Run the microservice
$ java -jar target/dropwizarddemo-1.0-SNAPSHOT.jar server sample.yml

30.  Test in the browser by navigating to
http://localhost:8080/app/greetings/Robert

31.  Second test by navigating to
http://localhost:8080/app/getClient/Robert


