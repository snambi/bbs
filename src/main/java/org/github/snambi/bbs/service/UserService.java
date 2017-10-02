package org.github.snambi.bbs.service;

import org.github.snambi.bbs.entity.User;
import org.github.snambi.bbs.entity.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 */
@Component
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUser( String email ){

        User user = userDao.findByEmail(email);

        return user;
    }

    public User getUser( long id ){
        return userDao.findOne(id);
    }

    public Collection<User> getAllUsers(){

        List<User> result = new ArrayList<>();

        Iterator<User> iterator = userDao.findAll().iterator();
        while( iterator.hasNext() ){
            result.add(iterator.next());
        }

        return result;
    }
}
