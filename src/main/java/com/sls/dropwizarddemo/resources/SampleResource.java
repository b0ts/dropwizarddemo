package com.sls.dropwizarddemo.resources;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

import com.sls.dropwizarddemo.EmployeeDb;
import com.sls.dropwizarddemo.Employee;

@Path("/app/")
public class SampleResource {

    public SampleResource(Object p0) {
    }
    @GET
    @Path("/greetings/{name}")
    public String getGreetings(@PathParam("name") String name) {
        Gson gson = new Gson();
        return gson.toJson("Hello, " + name + "!");
    }
    @GET
    @Path("/getClient/{name}")
    public String getClient(@PathParam("name") String name) {
        // stubbed for now to return the same json for each client
        String json = "{\"name\":\"Goober\",\"height\":\"167\",\"mass\":\"180\",\"hair_color\":\"brown\",\"skin_color\":\"white\",\"eye_color\":\"hazle\",\"birth_year\":\"1961\",\"gender\":\"m\",\"url\":\"https://www.sweetlightstudios.com\"}\n";
        return json;
    }
    @GET
    @Path("/employee/{id}")
    public Response getEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeDb.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Path("/employee")
    public Response createEmployee(Employee employee) throws URISyntaxException {
        Employee e = EmployeeDb.getEmployee(employee.getId());
        if (e != null) {
            EmployeeDb.updateEmployee(employee.getId(), employee);
            return Response.created(new URI("/employees/" + employee.getId()))
                    .build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/employee/{id}")
    public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
        Employee e = EmployeeDb.getEmployee(employee.getId());
        if (e != null) {
            employee.setId(id);
            EmployeeDb.updateEmployee(id, employee);
            return Response.ok(employee).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/employee/{id}")
    public Response removeEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeDb.getEmployee(id);
        if (employee != null) {
            EmployeeDb.removeEmployee(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}
