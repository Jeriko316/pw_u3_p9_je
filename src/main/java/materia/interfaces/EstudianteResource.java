package materia.interfaces;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import materia.application.EstudianteService;
import materia.application.HijoService;
import materia.application.representation.EstudianteRepresentation;
import materia.application.representation.HijoRepresentation;
import materia.application.representation.LinkDto;
import materia.domain.Estudiante;

@Path("/estudiantes")
@RolesAllowed("admin")
public class EstudianteResource {
 
    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;
 

      @Inject
    private UriInfo uriInfo;

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
  
    public List<EstudianteRepresentation> listarTodos(){
        List<EstudianteRepresentation> lista= estudianteService.listarTodos();
        lista.forEach(this::construirLinks);
        return lista;
    }

 
    @GET
    @Path("/consultarPorId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id){
        EstudianteRepresentation er = estudianteService.consultarPorId(id);
        if (er == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(construirLinks(er)).build();
    }


      @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaId(@PathParam("id") Integer iden) {
        EstudianteRepresentation er = estudianteService.consultarPorId(iden);
        if (er == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(construirLinks(er)).build();
    }
 
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(EstudianteRepresentation estu){
        this.estudianteService.crear(estu);
        return Response.status(Response.Status.CREATED).entity(estu).build();
    }
 
    @PUT
    @Path("/actualizar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") Integer id, Estudiante estu){
        this.estudianteService.actualizar(id, estu);
        return Response.status(209).entity(null).build();
    }
 
    @PATCH
    @Path("/actualizarParcial/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }

     @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarHijosPorEstudiante(@PathParam("id") Integer id) {
        List<HijoRepresentation> hijos = hijoService.buscarPorIdEstudiante(id);
        if (hijos == null || hijos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hijos).build();
    }

 // Método para construir enlaces HATEOAS
    private EstudianteRepresentation construirLinks(EstudianteRepresentation er) {
        String self = this.uriInfo.getBaseUriBuilder()
                .path(EstudianteResource.class)
                .path(String.valueOf(er.getId()))
                .build()
                .toString();

        String hijos = this.uriInfo.getBaseUriBuilder()
                .path(EstudianteResource.class)
                .path(String.valueOf(er.getId()))
                .path("hijos")
                .build()
                .toString();

        er.links = List.of(
                new LinkDto(self, "self"),
                new LinkDto(hijos, "hijos"));
        return er;
    }
}

