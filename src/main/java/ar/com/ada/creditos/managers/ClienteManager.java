package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.*;

public class ClienteManager {

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Cliente cliente) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(cliente);

        session.getTransaction().commit();
        session.close();
    }

    public Cliente read(int clienteId) {
        Session session = sessionFactory.openSession();

        Cliente cliente = session.get(Cliente.class, clienteId);

        session.close();

        return cliente;
    }

    public Cliente readByDNI(int dni) {
        Session session = sessionFactory.openSession();

        Cliente cliente = session.byNaturalId(Cliente.class).using("dni", dni).load();

        session.close();

        return cliente;
    }

    public void update(Cliente cliente) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(cliente);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Cliente cliente) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(cliente);

        session.getTransaction().commit();
        session.close();
    }

    public List<Cliente> buscarTodos() {

        Session session = sessionFactory.openSession();

        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM cliente", Cliente.class);

        List<Cliente> todos = query.getResultList();

        return todos;

    }

    public List<Cliente> buscarPor(String nombre) {

        Session session = sessionFactory.openSession();

        Query query= session.createNativeQuery("SELECT * FROM cliente where nombre = ?");
        

        List<Cliente> clientesHackeado = query.getResultList();

        Query queryConParametrosSql= session.createNativeQuery("SELECT * FROM cliente where nombre = ?" , Cliente.class);
        queryConParametrosSql.setParameter(1, nombre);
        return clientesHackeado;
    }
   
   


//Cuenta cantidad total de clientes
public int contarClienteQueryNativa(){
    Session session = sessionFactory.openSession();

    Query query = session.createNativeQuery("SELECT count(*) FROM cliente");

    int resultado = ((Number)query.getSingleResult()).intValue();
    return resultado;

}

}
