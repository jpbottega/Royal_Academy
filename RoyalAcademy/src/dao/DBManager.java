package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public class DBManager {
	

	public boolean aux_upd(String iupdate) throws Exception {
		Session session = null;
		boolean result = false;
		try {
			session =  HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			NativeQuery sqlQuery = session.createSQLQuery(iupdate);
			sqlQuery.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			try {
				session.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw e;
		} finally {
			if (session != null)
				try {
					session.close();
				} catch (Exception logOrIgnore) {
				}
		}
		return result;
	}

	public boolean delete_tabla(Object reg) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		try {
			session.delete(reg);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			result = false;
			// TODO: handle exception
			try {
				session.getTransaction().rollback();
				if (session != null)
					session.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw e;
		} finally {
			try {
				if (session != null)
					session.close();
			} catch (Exception e2) {
			}
		}
		return result;

	}

	public boolean save_tabla(Object reg) {

		Session session =  HibernateUtil.getSessionFactory().openSession();
		boolean result = false;
		try {
			session.beginTransaction();
			session.saveOrUpdate(reg);
			session.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (session != null) {
					if (session.getTransaction() != null)
						session.getTransaction().rollback();
				}
			} catch (Exception xx) {

			}
		} finally {
			if (session != null)
				try {
					session.close();
				} catch (Exception logOrIgnore) {
				}
		}

		return result;
	}

	public void aux_upd(String iupdate, String iusuario) {
		Connection consgh = null;
		Session session = null;
		try {
			session =  HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			NativeQuery sqlQuery = session.createSQLQuery(iupdate);
			sqlQuery.executeUpdate();
			session.getTransaction().commit();
			sqlQuery = null;
		} catch (Exception x) {
			x.printStackTrace();
			// system.out.println(x.toString());
			try {
				if (session != null) {
					if (session.getTransaction() != null)
						session.getTransaction().rollback();
				}
			} catch (Exception xx) {
			}
		} finally {
			if (session != null)
				try {
					session.close();
				} catch (Exception logOrIgnore) {
				}
			if (consgh != null)
				try {
					consgh.close();
				} catch (Exception logOrIgnore) {
				}
		}
	}

	public String aux_select_string(String iquery) {
		Session session =HibernateUtil.getSessionFactory().openSession();
		String result = "";
		try {
			result = (String) session.createSQLQuery(iquery).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public int aux_select_int(String iquery) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		int result = 0;
		try {
			result = (Integer) session.createSQLQuery(iquery).uniqueResult();
			session.getTransaction().commit();

		} catch (Exception e) {
			// TODO: handle exception
			try {
				session.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	
}
