package com.ilya.crudapp.repository.hibernate;

import com.ilya.crudapp.connectbd.HibernateConnetor;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.WriterRepository;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class HbWriterRepositoryImpl implements WriterRepository {
    @Override
    public List<Writer> getAll() {
        Session session;
        Transaction transaction ;
        List<Writer> result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.createQuery("FROM "+Writer.class.getSimpleName()).list();

        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }

        transaction.commit();
        session.close();
        return result.stream().filter(x-> x.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
    }

    @Override
    public Writer getById(Long aLong) {
        Session session;
        Transaction transaction ;
        Writer result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.get(Writer.class, aLong);
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
            Writer writer = session.get(Writer.class, aLong);
            writer.setStatus(Status.DELETED);
            session.update(writer);

        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Writer update(Writer writer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            writer.setStatus(Status.ACTIVE);
            session.update(writer);
        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return getById(writer.getId());
    }

    @Override
    public Writer save(Writer writer) {
        Session session;
        Transaction transaction;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            Long id = (Long) session.save(writer);
            writer.setId(Long.valueOf(id));
        } catch (HibernateError e){
            throw new HibernateError(e.getMessage());
        }
        transaction.commit();
        session.close();
        return writer;
    }
}
