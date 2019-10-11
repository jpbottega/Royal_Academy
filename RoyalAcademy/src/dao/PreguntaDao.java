package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import modelo.Opciones_Pregunta;
import modelo.Pregunta;


public class PreguntaDao extends DBManager{
	
	public boolean insertarPregunta(Pregunta pregunta) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerPreguntaPorDenominacion(pregunta.getPregunta()) == null) {
				t = sesion.beginTransaction();
				sesion.save(pregunta);
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

	public boolean eliminarPregunta(Pregunta pregunta) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerPreguntaPorDenominacion(pregunta.getPregunta()) != null) {
				t = sesion.beginTransaction();
				sesion.delete(pregunta);
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

	public Pregunta traerPreguntaPorId(int id) {
		Pregunta pregunta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from preguntas where id = :id").addEntity(Pregunta.class)
						.setParameter("id", id);
				pregunta = (Pregunta) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return pregunta;
		}
		return pregunta;
	}

	public Pregunta traerPreguntaPorDenominacion(String p) {
		Pregunta pregunta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from preguntas where pregunta = :pregunta")
					.addEntity(Pregunta.class).setParameter("pregunta", p);
			pregunta = (Pregunta) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return pregunta;
		}
		return pregunta;
	}
	public List<Pregunta> traerPreguntaPorCarreraCurso(int carrera,int curso) {
		List<Pregunta> pregunta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			pregunta = (List<Pregunta>) sesion.createSQLQuery("select * from preguntas where id_carrera ="+carrera+" and id_curso="+curso)
					.addEntity(Pregunta.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return pregunta;
		}
		return pregunta;
	}
	
	public List<Opciones_Pregunta> traerOpciones(int id_pregunta) {
		List<Opciones_Pregunta> opciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			opciones = (List<Opciones_Pregunta>) sesion.createSQLQuery("select * from opciones_pregunta where id_pregunta ="+id_pregunta)
					.addEntity(Opciones_Pregunta.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return opciones;
	}
	
	public List<Pregunta> traerTodos(){
		List<Pregunta> pregunta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from preguntas")
					.addEntity(Pregunta.class);
			pregunta = (List<Pregunta>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return pregunta;
		}
		return pregunta;
	}
}
