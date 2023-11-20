package com.ilya.crudapp.repository.hibernate;

import com.ilya.crudapp.connectbd.HibernateConnetor;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class HbPostRepositoryImpl implements PostRepository {
    @Override
    public List<Post> getAll() {
        Session session;
        Transaction transaction ;
        List<Post> result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.createQuery("FROM "+Post.class.getSimpleName()).list();
        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return result.stream().filter(x-> x.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
    }

    @Override
    public Post getById(Long aLong) {
        Session session;
        Transaction transaction ;
        Post result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.get(Post.class, aLong);
        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public boolean deleteById(Long aLong) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, aLong);
            post.setStatus(Status.DELETED);
            session.update(post);

        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Post save(Post post) {
        Session session;
        Transaction transaction;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            Long id = (Long) session.save(post);
            post.setId(Long.valueOf(id));
        } catch (HibernateError e){
            throw new HibernateError(e.getMessage());
        }
        transaction.commit();
        session.close();
        return post;
    }

    @Override
    public Post update(Post post) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            post.setStatus(Status.ACTIVE);
            session.update(post);
        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return getById(post.getId());
    }
}
