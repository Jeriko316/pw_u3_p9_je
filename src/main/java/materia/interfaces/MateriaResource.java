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
import materia.application.MateriaService;
import materia.domain.Materia;
// import net.bytebuddy.asm.Advice.Return; // eliminado: import innecesario

@Path("/materias")
public class MateriaResource {
    
    @Inject
    private MateriaService materiaService;

    @GET
    @Path("/todos")
    public List<Materia> buscarTodos(){
        return this.materiaService.listaTodos();
    }

    @GET
    @Path("/buscar/{nombre}")
    public List<Materia> buscarPorNombre(@PathParam("nombre") String nombre){
        return this.materiaService.buscarPorNombre(nombre);
    }

    @GET
    @Path("/buscar/creditos/{creditos}")
    public List<Materia> buscarPorCreditos(@PathParam("creditos") String creditos){
        return this.materiaService.buscarPorCreditos(creditos);
    }


    @GET
    @Path("/consultarPorId/{id}")
    public Materia consultarPorId(@PathParam("id") Integer id){
        return this.materiaService.consultarPorId(id);
    }

    @POST
    @Path("/crear")
    public void guardar(Materia materia){
        this.materiaService.crear(materia);
    }

    @PUT
    @Path("/actualizar/{id}")
    public void actualizar(@PathParam("id") Integer id, Materia materia){
        this.materiaService.actualizar(id, materia);
    }
    

    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Materia materia){
        this.materiaService.actualizarParcial(id, materia);
    }

    @DELETE
    @Path("/borrar/{id}")
    public void borrar(@PathParam("id") Integer id){
        this.materiaService.eliminar(id);
    }
}
