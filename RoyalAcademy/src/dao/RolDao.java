package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Rol;


public class RolDao extends DBManager {
	
	public boolean insertarRol(Rol r) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerRolPorDenominacion(r.getDenominacion()) == null) {
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

	public boolean eliminarRol(Rol r) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerRolPorDenominacion(r.getDenominacion()) != null) {
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

	public Rol traerRolPorId(int id) {
		Rol rol = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				NativeQuery query = sesion.createSQLQuery("select * from roles where id = :id").addEntity(Rol.class)
						.setParameter("id", id);
				rol = (Rol) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return rol;
		}
		return rol;
	}

	public Rol traerRolPorDenominacion(String denominacion) {
		Rol rol = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from roles where denominacion = :denominacion").addEntity(Rol.class)
					.setParameter("denominacion", denominacion);
			rol = (Rol) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return rol;
	}
	
	public List<Rol> traerTodos(){
		List<Rol> roles = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from roles")
					.addEntity(Rol.class);
			roles = (List<Rol>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return roles;
	}
}
