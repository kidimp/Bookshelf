package com.chous.bookservice.component;

import com.chous.bookservice.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateSession {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateSession(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void cacheTest() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            logger.info("Fetching Book with id 1 from the database...");
            Book book1 = session.get(Book.class, 1L);
            logger.info("Book 1 Title: {}", book1.getTitle());

            logger.info("Fetching Book with id 1 again...");
            Book book2 = session.get(Book.class, 1L);
            logger.info("Book 2 Title: {}", book2.getTitle());

            logger.info("Updating Book 1 title...");
            book1.setTitle("Updated Title");

            logger.info("Flushing changes to the database...");
            session.flush();

            logger.info("Fetching Book with id 1 again after update...");
            Book book3 = session.get(Book.class, 1L);
            logger.info("Book 3 Title: {}", book3.getTitle());

            logger.info("Committing transaction...");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error occurred: ", e);
        } finally {
            logger.info("Closing session...");
            session.close();
        }
    }

}
