package materia.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import materia.application.representation.EstudianteRepresentation;
import materia.domain.Estudiante;
import materia.domain.Materia;
import materia.infraestructure.EstudianteRepository;
import materia.infraestructure.MateriaRepository;
import materia.interfaces.EstudianteResource;

@ApplicationScoped
public class EstudianteService {
 
    @Inject
    private EstudianteRepository estudianteRepository;
 
    public List<EstudianteRepresentation> listarTodos() {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for(Estudiante estu:this.estudianteRepository.listAll())
            list.add(this.mapperToEr(estu));
        return list;
    }
 
    public EstudianteRepresentation consultarPorId(Integer id){
        Estudiante estu = this.estudianteRepository.findById(id.longValue());
        if (estu == null) {
            return null;
        }
        return this.mapperToEr(estu);
    }
    
    public Estudiante consultarPorIdEntity(Integer id){
        return this.estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(EstudianteRepresentation estu){
        this.estudianteRepository.persist(this.mapperToEstudiante(estu));
    }
 
    @Transactional
    public void actualizar(Integer id, Estudiante est){
        Estudiante estu = this.consultarPorIdEntity(id);
        estu.setApellido(est.getApellido());
        estu.setNombre(est.getNombre());
        estu.setFechaNacimiento(est.getFechaNacimiento());
    }
 
    @Transactional
    public void actualizarParcial(Integer id, Estudiante est){
        Estudiante estu=this.consultarPorIdEntity(id);
        if(est.getNombre()!=null){
            estu.setNombre(est.getNombre());
        }
        if(est.getApellido()!=null){
            estu.setApellido(est.getApellido());
        }
        if(est.getFechaNacimiento()!=null){
            estu.setFechaNacimiento(est.getFechaNacimiento());
        }
    }
   
    @Transactional
    public void eliminar(Integer id){
        this.estudianteRepository.deleteById(id.longValue());
    }
 
    public List<Estudiante> buscarPorProvincia(String provincia, String genero){
       // return this.estudianteRepository.find("provincia", provincia).list();
       return this.estudianteRepository.find("provincia= ?1 and genero=?2", provincia, genero).list();
       
    }

    private EstudianteRepresentation mapperToEr(Estudiante estu){
        EstudianteRepresentation estuR = new EstudianteRepresentation();
        estuR.setId(estu.getId());
        estuR.setNombre(estu.getNombre());
        estuR.setApellido(estu.getApellido());
        estuR.setFechaNacimiento(estu.getFechaNacimiento());
        estuR.setGenero(estu.genero);
        estuR.setProvincia(estu.getProvincia());
        return estuR;
    }

     private Estudiante mapperToEstudiante(EstudianteRepresentation estu){
        Estudiante estuR = new Estudiante();
        estuR.setId(estu.getId());
        estuR.setNombre(estu.getNombre());
        estuR.setApellido(estu.getApellido());
        estuR.setFechaNacimiento(estu.getFechaNacimiento());
        estuR.genero = estu.getGenero();
        estuR.setProvincia(estu.getProvincia());
        return estuR;
    }
}
