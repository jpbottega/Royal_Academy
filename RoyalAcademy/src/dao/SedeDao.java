package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import modelo.Sede;


public class SedeDao extends DBManager {
	public boolean insertarSede(Sede r) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerSedePorDenominacion(r.getSede()) == null) {
				t = sesion.beginTransaction();
				sesion.save(r);
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

	public boolean eliminarSede(Sede r) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerSedePorDenominacion(r.getSede()) != null) {
				t = sesion.beginTransaction();
				sesion.delete(r);
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

	public Sede traerSedePorId(int id) {
		Sede p = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from sedes where id = :id").addEntity(Sede.class)
						.setParameter("id", id);
				p = (Sede) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return p;
		}
		return p;
	}

	public Sede traerSedePorDenominacion(String denominacion) {
		Sede p = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from sedes where sede = :sede")
					.addEntity(Sede.class).setParameter("sede", denominacion);
			p = (Sede) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public List<Sede> traerTodos(){
		List<Sede> sedes = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from sedes")
					.addEntity(Sede.class);
			sedes = (List<Sede>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sedes;
	}
}
