package materia.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import materia.application.AuthClient;
import materia.domain.Usuario;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/auth")
public class AuthResource {

    @Inject
    @RestClient
    AuthClient authClient;

  @GET
@Path("/login")
public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
    try {
        AuthClient.TokenResponse tokenResponse = authClient.getToken(username, password);
        return Response.ok(tokenResponse).build();
    } catch (Exception e) {
        return Response.status(401).entity("Credenciales inválidas").build();
    }
}
}