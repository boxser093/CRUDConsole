package com.ilya.crudapp.repository.hibernate;

import com.ilya.crudapp.connectbd.HibernateConnetor;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.LabelRepository;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class HbLabelRepositoryImpl implements LabelRepository {
    @Override
    public List<Label> getAll() {
        Session session;
        Transaction transaction ;
        List<Label> result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.createQuery("FROM "+Label.class.getSimpleName()).list();

        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }

        transaction.commit();
        session.close();
        return result.stream().filter(x-> x.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
    }

    @Override
    public Label getById(Long aLong) {
        Session session;
        Transaction transaction ;
        Label result;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            result = (Label) session.get(Label.class, aLong);
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
            Label label = (Label) session.get(Label.class, aLong);
            label.setStatus(Status.DELETED);
            session.update(label);

        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
            transaction.commit();
            session.close();
        return true;
    }

    @Override
    public Label update(Label label) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            label.setStatus(Status.ACTIVE);
            session.update(label);
        } catch (HibernateError e){
            throw new HibernateError (e.getMessage());
        }
        transaction.commit();
        session.close();
        return getById(label.getId());
    }

    @Override
    public Label save(Label label) {
        Session session;
        Transaction transaction;
        try {
            session = HibernateConnetor.getCurrentSession();
            transaction = session.beginTransaction();
            Long id = (Long) session.save(label);
            label.setId(Long.valueOf(id));
        } catch (HibernateError e){
            throw new HibernateError(e.getMessage());
        }
        transaction.commit();
        session.close();
        return label;
    }
}
