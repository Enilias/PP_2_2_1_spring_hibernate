package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public User getUserByCar(String model, int series) {
        Session session = sessionFactory.getCurrentSession();
        List<User> list = (List<User>) session.createQuery("from User where car.model=:model and car.series=:series")
                .setParameter("model", model).setParameter("series", series).getResultList();
        return !list.isEmpty() ? list.get(0) : null;

    }

}
