package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import modelo.CursoExamen;
import modelo.ExamenResolucion;
import modelo.InscripcionExamen;
import modelo.Sede;

public class CursoExamenDao extends DBManager {
	
	public CursoExamen traerCursoExamenPorId(int id) {
		CursoExamen carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from cursoexamen where id = :id").addEntity(CursoExamen.class)
						.setParameter("id", id);
				carrera = (CursoExamen) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<CursoExamen> traerExamenesPorCurso(int id_curso){
		List<CursoExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from cursoexamen where id_curso = :id")
					.addEntity(CursoExamen.class).setParameter("id", id_curso);
			carrera = (List<CursoExamen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<CursoExamen> traerExamenesPorAlumno(int id_usuario){
		List<CursoExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from cursoexamen where id in (select id_cursoexamen from inscripcion_examen where entregado = 0 and id_usuario = :id)")
					.addEntity(CursoExamen.class).setParameter("id", id_usuario);
			carrera = (List<CursoExamen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public InscripcionExamen traerInscripcionExamen(int id_usuario, int id_curso_examen) {
		InscripcionExamen carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id_usuario != 0 && id_curso_examen != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from inscripcion_examen where id_usuario = :id and id_cursoexamen = :idCurso")
						.addEntity(InscripcionExamen.class).setParameter("id", id_usuario).setParameter("idCurso", id_curso_examen);
				carrera = (InscripcionExamen) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<InscripcionExamen> traerInscripcionExamenPorCurso(int id_curso_examen) {
		List<InscripcionExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id_curso_examen != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from inscripcion_examen where id_cursoexamen = :idCurso")
						.addEntity(InscripcionExamen.class).setParameter("idCurso", id_curso_examen);
				carrera = (List<InscripcionExamen>) query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<ExamenResolucion> traerExamenesResueltos(int id_usuario, int id_curso_examen) {
		List<ExamenResolucion> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id_usuario != 0 && id_curso_examen != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from examenresolucion where id_alumno = :id and id_curso_examen = :idCurso")
						.addEntity(ExamenResolucion.class).setParameter("id", id_usuario).setParameter("idCurso", id_curso_examen);
				carrera = (List<ExamenResolucion>) query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public void bulkDeleteExamenRes(List<ExamenResolucion> ex){
		
	}
	
	public List<CursoExamen> traerTodos(){
		List<CursoExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from cursoexamen")
					.addEntity(CursoExamen.class);
			carrera = (List<CursoExamen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
}
