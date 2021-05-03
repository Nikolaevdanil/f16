package com.example.demo.service;

import com.example.demo.models.Manufacture;
import com.example.demo.models.Worker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@org.springframework.stereotype.Service
public class Servicee implements ServiceInterface  {
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public void addManufacture(Manufacture manufacture) {
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.saveOrUpdate(manufacture);
        transaction.commit();
        session.close();

    }

    @Override
    public void addWorker(Worker worker) {
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.saveOrUpdate(worker);
        transaction.commit();
        session.close();
    }

    @PostConstruct
    @Override
    @JsonIgnore
    public List<Manufacture> getAllManufacture() {
        Session session = sessionFactory.openSession();
        List<Manufacture> manufactures = session.createQuery("select b from Manufacture b", Manufacture.class).getResultList();
        session.close();
        return manufactures;
    }

    @PostConstruct
    @Override
    @JsonIgnore
    public List<Worker> getAllWorker() {
        Session session = sessionFactory.openSession();
        List<Worker> workers = session.createQuery("select c from Worker c", Worker.class).getResultList();
        session.close();
        return workers;
    }

    @Override
    @JsonIgnore
    public Manufacture getManufactureById(Long id) {
        Manufacture buff = new Manufacture();
        List<Manufacture> banks = getAllManufacture();
        for (Manufacture item:banks) {
            if(id == item.getId()){
                buff = item;
            }
        }
        return  buff;
    }

    @Override
    @JsonIgnore
    public Worker getWorkerById(Long id) {
        List<Worker> cards = getAllWorker();
        Worker buff = new Worker();
        for (Worker item:cards) {
            if(id == item.getId()){
                buff = item;
            }
        }
        return buff;
    }

    @Override
    public void deleteManufactureById(Long id) {
        Manufacture buff = new Manufacture();
        List<Manufacture> banks = getAllManufacture();
        for (Manufacture item:banks) {
            if(id == item.getId()){
                buff = item;
            }
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(session.get(Manufacture.class, buff.getId()));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAllManufactures() {

    }

    @Override
    public void deleteWorkerById(Long id) {
        List<Worker> cards = getAllWorker();
        Worker buff = new Worker();
        for (Worker item:cards) {
            if(id == item.getId()){
                buff = item;
            }
        }
        System.out.println(buff.getFirstName());
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(session.get(Worker.class, buff.getId()));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAllWorkers() {

    }

    @Override
    public Manufacture getBanByManufacture(Long worker_id) {
        Manufacture buff;
        Session session = sessionFactory.openSession();
        buff = session.createQuery("select c from Worker c where c.id = :id", Worker.class)
                .setParameter("id",worker_id).getSingleResult().getManufacture();
        return buff;
    }

    @Override
    public Long getManufactureId(String name) {
        List<Manufacture> banks = getAllManufacture();
        Long buff = new Long(0);
        for (Manufacture item:banks) {
            if(name.equals(item.getName())){
                buff = item.getId();
            }
        }
        return buff;
    }

    @Override
    public Long getWorkerId(String  firstName) {
        List<Worker> cards = getAllWorker();
        Long buff = new Long(0);
        for (Worker item:cards) {
            if(firstName.equals(item.getFirstName())){
                buff = item.getId();
            }
        }
        return buff;
    }
}
