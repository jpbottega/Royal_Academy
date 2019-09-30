package dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Examen;
import modelo.Pregunta;
import modelo.PreguntaxExamen;
import modelo.Sede;


public class ExamenDao extends DBManager{
	public boolean insertarExamen(Examen examen) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerExamenPorId(examen.getId()) == null) {
				t = sesion.beginTransaction();
				sesion.save(examen);
				t.commit();
				insertado = true;
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return insertado;
		}
		return insertado;
	}

	public boolean eliminarExamen(Examen examen) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerExamenPorId(examen.getId()) != null) {
				t = sesion.beginTransaction();
				sesion.delete(examen);
				t.commit();
				eliminado = true;
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return eliminado;
		}
		return eliminado;
	}

	public Examen traerExamenPorId(int id) {
		Examen examen = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from examenes where id = :id").addEntity(Examen.class)
						.setParameter("id", id);
				examen = (Examen) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return examen;
		}
		return examen;
	}

	public List<Examen> traerExamenPorCreador(int id_usuario_creador) {
		List<Examen> examen = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from examenes where id_usuario_creador = :id_usuario_creador")
					.addEntity(Examen.class).setParameter("id_usuario_creador", id_usuario_creador);
			examen = (List<Examen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return examen;
		}
		return examen;
	}
	
	public List<Examen> traerTodos(){
		List<Examen> examen = new ArrayList<Examen>();
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from examenes")
					.addEntity(Examen.class);
			examen = (List<Examen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return examen;
		}
		return examen;
	}

	public List<Pregunta> traerPreguntasHabilidatas(int id_examen) {
		List<Pregunta> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call preguntasHabilitadas("+id_examen+")").addEntity(Pregunta.class);
				Funciones = (List<Pregunta>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}

	public List<Pregunta> traerPreguntasDisponibles(int id_examen) {
		List<Pregunta> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call preguntasDisponibles("+id_examen+")").addEntity(Pregunta.class);
				Funciones = (List<Pregunta>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}

	public List<Examen> traerExamenPorCurso(int id_curso) {
		List<Examen> examen = new ArrayList<Examen>();
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from examenes where id_curso = :id_usuario_creador")
					.addEntity(Examen.class).setParameter("id_usuario_creador", id_curso);
			examen = (List<Examen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return examen;
		}
		return examen;
	}

	public boolean eliminarPreguntasAsociadas(int id_examen) {
		boolean eliminadas = false;
		Transaction t = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			t = sesion.beginTransaction();
			NativeQuery query = sesion.createSQLQuery("call eliminarPreguntasAsociadasExamen("+id_examen+")");
			query.executeUpdate();
			t.commit();
			eliminadas = true;
		} catch (Exception e) {
			e.printStackTrace();
			return eliminadas;
		}
		return eliminadas;
	}
}
