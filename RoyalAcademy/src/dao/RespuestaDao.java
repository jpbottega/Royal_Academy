package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import modelo.Respuesta;


public class RespuestaDao {
	
	public boolean insertarRespuesta(Respuesta respuesta) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerRespuestaPorRespuesta(respuesta.getRespuesta()) == null) {
				t = sesion.beginTransaction();
				sesion.save(respuesta);
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

	public boolean eliminarRespuesta(Respuesta respuesta) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerRespuestaPorRespuesta(respuesta.getRespuesta()) != null) {
				t = sesion.beginTransaction();
				sesion.delete(respuesta);
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

	public Respuesta traerRespuestaPorId(int id) {
		Respuesta respuesta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from respuestas where id = :id").addEntity(Respuesta.class)
						.setParameter("id", id);
				respuesta = (Respuesta) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return respuesta;
		}
		return respuesta;
	}

	public Respuesta traerRespuestaPorRespuesta(String r) {
		Respuesta respuesta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from respuestas where respuesta = :respuesta")
					.addEntity(Respuesta.class).setParameter("respuesta", r);
			respuesta = (Respuesta) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return respuesta;
		}
		return respuesta;
	}
	
	public List<Respuesta> traerTodos(){
		List<Respuesta> respuesta = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from respuestas")
					.addEntity(Respuesta.class);
			respuesta = (List<Respuesta>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return respuesta;
		}
		return respuesta;
	}
}
