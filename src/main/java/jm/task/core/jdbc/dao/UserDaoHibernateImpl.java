package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
    Util util = new Util();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            util.getFactory().getCurrentSession().beginTransaction();
            util.getFactory().getCurrentSession().save(new User(name, lastName, age));
            util.getFactory().getCurrentSession().getTransaction().commit();
            System.out.println("Миньён c именем " + name + " добавлен в таблицу");
        } catch (Exception e) {
            e.printStackTrace();
            util.getFactory().getCurrentSession().getTransaction().rollback();
        }


    }

    @Override
    public void removeUserById(long id) {
        try {
            util.getFactory().getCurrentSession().beginTransaction();
            util.getFactory().getCurrentSession().delete(util.getFactory().getCurrentSession().get(User.class, id));
            util.getFactory().getCurrentSession().getTransaction().commit();
            System.out.println("Миньён под номером :" + id + " был удален.");
        } catch (Exception e) {
            e.printStackTrace();
            util.getFactory().getCurrentSession().getTransaction().rollback();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> minions = null;
        try {
            util.getFactory().getCurrentSession().beginTransaction();
            minions = util.getFactory().getCurrentSession().createQuery("from User").getResultList();
            util.getFactory().getCurrentSession().getTransaction().commit();
            System.out.println(minions);

        } catch (Exception e) {
            e.printStackTrace();
            util.getFactory().getCurrentSession().getTransaction().rollback();
        }
        return minions;
    }

    @Override
    public void cleanUsersTable() {
        util.getFactory().getCurrentSession().beginTransaction();
        util.getFactory().getCurrentSession().createQuery("delete User").executeUpdate();
        util.getFactory().getCurrentSession().getTransaction().commit();
    }
}