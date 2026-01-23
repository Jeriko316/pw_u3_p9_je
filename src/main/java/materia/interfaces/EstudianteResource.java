package materia.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import materia.application.EstudianteService;
import materia.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {
 
    @Inject
    private EstudianteService estudianteService;
 
    @GET
    @Path("/todos")
    public List<Estudiante> listarTodos(){
        return this.estudianteService.listarTodos();
    }
 
    @GET
    @Path("/consultarPorId/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer id){
        return this.estudianteService.consultarPorId(id);
    }
 
    @POST
    @Path("/crear")
    public Response guardar(Estudiante estu){
        this.estudianteService.crear(estu);
        return Response.status(Response.Status.CREATED).entity(estu).build();
    }
 
    @PUT
    @Path("/actualizar/{id}")
    public Response actualizar(@PathParam("id") Integer id, Estudiante estu){
        this.estudianteService.actualizar(id, estu);
        return Response.status(209).entity(null).build();
    }
 
    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Estudiante estu){
        this.estudianteService.actualizarParcial(id, estu);
    }
 
    @DELETE
    @Path("borrar/{id}")
    public void borrar(@PathParam("id") Integer id){
        this.estudianteService.eliminar(id);
    }
 
   
    @GET
    @Path("/buscarPorProvinciaYGenero")
    public List<Estudiante> buscarPorProvinciaYGenero(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }

    @GET
    @Path("/buscarPorProvincia")
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }
 
}

