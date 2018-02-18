package ir.maktab.model.book.dao;

import ir.maktab.model.author.Author;
import ir.maktab.model.author.dao.AuthorDao;
import ir.maktab.model.author.manager.AuthorManager;
import ir.maktab.model.book.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nader on 2/1/2018.
 */
@Repository
public class BookDao {

    @Autowired
    SessionFactory sessionFactory;

    public int add(Book book) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int res = (int) session.save(book);
        tx.commit();
        session.close();
        return res;
    }

    public Book getByIsbn(Long isbn) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Book book = session.get(Book.class, isbn);
        tx.commit();
        session.close();
        return book;
    }

    private List<Book> getAll(){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Book> books = session.createQuery("from Book").list();
        for (Book b :
                books) {
            System.out.println(b.getAuthor().getName());
        }
        tx.commit();
        session.close();
        return books;
    }

    public int edit(Book book) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(book);
            tx.commit();
            return 1;
        }catch (HibernateException e){
            return 0;
        }finally {
            session.close();
        }
    }

    public int delete(Long isbn) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("delete from Book where isbnNumber=" + isbn).executeUpdate();
            tx.commit();
            return 1;
        }catch (HibernateException e){
            return 0;
        }finally {
            session.close();
        }
    }

    public DefaultTableModel getAsModel(Book book) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Isbn", "Title", "Pages", "Reference", "AuthCode"}, 0);
        Book res = getByIsbn(book.getIsbnNumber());
        model.addRow(new Object[]{res.getIsbnNumber(), res.getTitle(), res.getPageNumber(), res.getReference(), res.getAuthor().getAuthorCode()});

        return model;
    }

    public DefaultTableModel getAllAsModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Isbn", "Title", "Pages", "Reference", "AuthCode"}, 0);
        List<Book> books = getAll();
        for (Book b :
                books) {
            model.addRow(new Object[]{b.getIsbnNumber(), b.getTitle(), b.getPageNumber(), b.getReference(), b.getAuthor().getAuthorCode()});
        }
        return model;
    }

    public Set<Book> getAuthorBooks(Integer authorId) {
        Session session = sessionFactory.openSession();
        Transaction tx =  session.beginTransaction();
        List<Book> books = session.createQuery("from Book where Author.getId="+authorId).list();
        tx.commit();
        session.close();
        return (Set<Book>) books;
    }

    public boolean exist(Book book) {
        return getByIsbn(book.getIsbnNumber()) != null;
    }



/*
    public int add(Book book) {
        String sql = "INSERT INTO book VALUES (?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, 0);
            ps.setLong(2, book.getIsbnNumber());
            ps.setString(3, book.getTitle());
            ps.setInt(4, book.getPageNumber());
            ps.setBoolean(5, book.getReference());
            ps.setInt(6, authorManager.load(book.getAuthor().getAuthorCode()).getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Book getByIsbn(Long isbn) {
        Book book = null;
        String sql = "SELECT * FROM book WHERE isbn = ?";
        String subSql = "SELECT * FROM author where id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement subPs = conn.prepareStatement(subSql)) {

            ps.setLong(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subPs.setInt(1, rs.getInt("author_id"));
                ResultSet rss = subPs.executeQuery();
                rss.next();
                Author author = new Author(rss.getLong("author_code"), rss.getString("name"),
                        rss.getInt("age"));
                book = new Book(rs.getLong("isbn_number"), rs.getString("title")
                        , rs.getInt("page_number"), rs.getBoolean("is_reference"), author);
                book.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public DefaultTableModel getAsModel(Book book) {
        DefaultTableModel model = null;
        String sql = "SELECT * FROM book WHERE isbn_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, book.getIsbnNumber());
            ResultSet rs = ps.executeQuery();
            model = new DefaultTableModel(new String[]{"Isbn", "Title", "Pages", "Reference", "AuthCode"}, 0);
            if (rs.next()) {
                model.addRow(new Object[]{book.getIsbnNumber(), rs.getString("title"), rs.getString("page_number")
                        , rs.getBoolean("is_reference"), rs.getLong("author_id")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getAllAsModel() {
        DefaultTableModel model = null;
        String sql = "SELECT * FROM book";
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet rs = stm.executeQuery(sql);
            model = new DefaultTableModel(new String[]{"Isbn", "Title", "Pages", "Reference", "AuthCode"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getLong("isbn_number"), rs.getString("title")
                        , rs.getString("page_number"), rs.getBoolean("is_reference")
                        , authorManager.getById(rs.getInt("author_id")).getAuthorCode()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public int edit(Book book) {
        String sql = "UPDATE book SET title = ?, page_number = ?, is_reference = ?, author_id = ? WHERE isbn_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getPageNumber());
            ps.setBoolean(3, book.getReference());
            ps.setInt(4, authorManager.load(book.getAuthor().getAuthorCode()).getId());
            ps.setLong(5, book.getIsbnNumber());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Long isbn) {
        String sql = "DELETE FROM book WHERE isbn_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, isbn);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> getAuthorBooks(Integer authorId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE author_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getLong("isbn_number"), rs.getString("title")
                        , rs.getInt("page_number"), rs.getBoolean("is_reference"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean exist(Book book) {
        String sql = "SELECT * FROM book WHERE isbn_number=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, book.getIsbnNumber());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    */
}
