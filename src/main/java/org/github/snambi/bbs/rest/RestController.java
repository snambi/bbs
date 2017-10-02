package org.github.snambi.bbs.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.snambi.bbs.entity.User;
import org.github.snambi.bbs.service.UserService;
import org.github.snambi.bbs.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 *
 */
@Component
@Path("/")
public class RestController {

    @Autowired
    VersionService versionService;

    @Autowired
    UserService userService;

    @GET
    @Path("/version")
    public String message() {
        return "{ 'version' : " + versionService.version() + " }";
    }

    @GET
    @Path("/hello")
    public String hello(){

        Date date = Calendar.getInstance().getTime();
        return "Hello "+ date;
    }

    @GET
    @Path("/user/{id}")
    public Response getUser(@PathParam("id") long id ){
        User user = userService.getUser(id);
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/user")
    public Response getAllUsers(){
        Collection<User> users = userService.getAllUsers();

        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
