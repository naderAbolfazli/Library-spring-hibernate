package ir.maktab.model.book.manager;

import ir.maktab.model.book.Book;
import ir.maktab.model.book.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Set;

/**
 * Created by Nader on 2/1/2018.
 */
@Service
public class BookManager {

    @Autowired
    BookDao bookDao;

    public int insert(Book book){
        return bookDao.add(book);
    }

    public Book load(Long isbn){
        return bookDao.getByIsbn(isbn);
    }

    public int update(Book book){
        return bookDao.edit(book);
    }

    public int delete(Long isbn){
        return bookDao.delete(isbn);
    }

    public Set<Book> getAuthorBooks(Integer authorId){
        return bookDao.getAuthorBooks(authorId);
    }

    public boolean exist(Book book) {
        return bookDao.exist(book);
    }

    public DefaultTableModel getAllAsModel() {
        return bookDao.getAllAsModel();
    }

    public TableModel getAsModel(Book book) {
        return bookDao.getAsModel(book);
    }
}
