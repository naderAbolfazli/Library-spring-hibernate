package ir.maktab.model.author.dao;

import ir.maktab.model.author.Author;
import ir.maktab.model.author.manager.AuthorManager;
import ir.maktab.model.book.Book;
import ir.maktab.model.book.dao.BookDao;
import ir.maktab.model.book.manager.BookManager;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import javax.sql.DataSource;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * Created by Nader on 2/6/2018.
 */
@Repository
public class AuthorDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    BookManager bookManager;

    public int add(Author author) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int res = (int) session.save(author);
        tx.commit();
        session.close();
        return res;
    }

    public Author getById(Integer author_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Author author = session.get(Author.class, author_id);
        tx.commit();
        session.close();
        return author;
    }

    public Author getByAuthorCode(Long authorCode) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Author.class);
        criteria.add(Restrictions.eq("authorCode", authorCode));
        Author author = (Author) criteria.list().get(0);
        tx.commit();
        session.close();
        return author;
    }

    private List<Author> getAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Author> authors = session.createQuery("from Author").list();
        tx.commit();
        session.close();
        return authors;
    }

    public int edit(Author author) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(author);
            tx.commit();
            return 1;
        } catch (HibernateException e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public int delete(Long authorCode) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("delete from Author where authorCode=" + authorCode).executeUpdate();
            tx.commit();
            return 1;
        } catch (HibernateException e) {
            return 0;
        } finally {
            session.close();
        }
    }

    public DefaultTableModel getAsModel(Author author) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"AuthorCode", "Name", "Age"}, 0);
        Author res = getByAuthorCode(author.getAuthorCode());
        model.addRow(new Object[]{res.getAuthorCode(), res.getName(), res.getAge()});

        return model;
    }

    public DefaultTableModel getAllAsModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"AuthorCode", "Name", "Age"}, 0);
        List<Author> authors = getAll();
        for (Author b :
                authors) {
            model.addRow(new Object[]{b.getAuthorCode(), b.getName(), b.getAge()});
        }
        return model;
    }

    public Author getBooks(Author author) {
        author.setId(getByAuthorCode(author.getAuthorCode()).getId());
        author.setBooks(bookManager.getAuthorBooks(author.getId()));
        return author;
    }

    public boolean exist(Author author) {
        return getByAuthorCode(author.getAuthorCode()) != null;
    }

}
