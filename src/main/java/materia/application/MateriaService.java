package materia.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import materia.domain.Materia;
import materia.infraestructure.MateriaRepository;

@ApplicationScoped
public class MateriaService {

    @Inject
    private MateriaRepository materiaRepository;

    public List<Materia> listaTodos(){
       return this.materiaRepository.listAll();
    }

    public Materia consultarPorId(Integer id){
        return this.materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(Materia materia){
        this.materiaRepository.persist(materia);
    }

    @Transactional
    public void actualizar(Integer id, Materia materia){
        Materia mat = this.consultarPorId(id);
        // actualizar con los valores recibidos
        mat.nombre = materia.nombre;
        mat.creditos = materia.creditos;
    }


    @Transactional
    public void actualizarParcial(Integer id, Materia materia){
        Materia mat = this.consultarPorId(id);
    if (materia.nombre != null) {
        mat.nombre = materia.nombre;
    }
    if (materia.creditos != null) {
        mat.creditos = materia.creditos;
    }
    }

    @Transactional
    public void eliminar(Integer id){
        this.materiaRepository.deleteById(id.longValue());
    }

 
    public List<Materia> buscarPorNombre(String nombre){
        return this.materiaRepository.seleccionarPorNombre(nombre);
    }

    public List<Materia> buscarPorCreditos(String creditos){
        return this.materiaRepository.seleccionarPorCreditos(creditos);
    }

    
}
