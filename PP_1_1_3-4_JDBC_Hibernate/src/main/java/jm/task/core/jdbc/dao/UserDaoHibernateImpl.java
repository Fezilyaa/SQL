package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private  SessionFactory sessionFactory;

    {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tr = null;
        try(Session session = sessionFactory.openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE NewSchema1.users (" +
                    " id INTEGER not null AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255)," +
                    " age INTEGER, " + "PRIMARY KEY(id))").executeUpdate();;
            tr.commit();
        } catch(HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        try(Session session = sessionFactory.openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS NewSchema1.users").executeUpdate();
            tr.commit();
        } catch(HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        Session session = sessionFactory.openSession();
        try {
            tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
            System.out.println("User с именем " + name +" добавлен в базу данных");
        } catch(HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        Session session = sessionFactory.openSession();
        try {
            tr = session.beginTransaction();
            session.remove(session.get(User.class, id));
            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        Transaction tr = null;
        Session session = sessionFactory.openSession();
        try {
            tr = session.beginTransaction();
            listOfUsers = session.createQuery("FROM User", User.class).list();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
            return listOfUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        Session session = sessionFactory.openSession();
        try {
            tr = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            tr.commit();
        } catch(HibernateException e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }
}
