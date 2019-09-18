package dao;

import org.hibernate.Transaction;
import modelo.Usuario;
import endec.StringEncrypter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class UsuarioDao {

	public boolean insertarUsuario(Usuario u) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerUsuarioPorMail(u.getEmail()) == null) {
				t = sesion.beginTransaction();
				sesion.save(u);
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

	public boolean eliminarUsuario(Usuario u) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			t = sesion.beginTransaction();
			sesion.delete(u);
			t.commit();
			eliminado = true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return eliminado;
		}
		return eliminado;
	}

	public boolean actualizarUsuario(Usuario u) {
		Transaction t = null;
		boolean actualizado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			t = sesion.beginTransaction();
			sesion.update(u);
			t.commit();
			actualizado = true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return actualizado;
		}
		return actualizado;
	}

	public Usuario traerUsuarioPorId(int id) {
		Usuario user = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from usuarios where id = :id").addEntity(Usuario.class)
						.setParameter("id", id);
				user = (Usuario) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
		return user;
	}

	public Usuario traerUsuarioPorMail(String email) {
		Usuario user = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from usuarios where email = :email")
					.addEntity(Usuario.class).setParameter("email", email);
			user = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
}
