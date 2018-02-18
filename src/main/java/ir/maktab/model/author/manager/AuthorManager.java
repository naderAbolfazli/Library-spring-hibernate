package ir.maktab.model.author.manager;

import ir.maktab.model.author.Author;
import ir.maktab.model.author.dao.AuthorDao;
import ir.maktab.model.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Set;

/**
 * Created by Nader on 2/6/2018.
 */
@Service
public class AuthorManager {

    @Autowired
    AuthorDao authorDao;

    public int insert(Author author){
        return authorDao.add(author);
    }

    public Author load(Long authorCode){
        return authorDao.getByAuthorCode(authorCode);
    }

    public int update(Author author){
        return authorDao.edit(author);
    }

    public int delete(Long author_code){
        return authorDao.delete(author_code);
    }

    public Set<Book> getBooks(Author author){
        return authorDao.getBooks(author).getBooks();
    }

    public boolean exist(Author author){
        return authorDao.exist(author);
    }

    public DefaultTableModel getAsModel(Author author){
        return authorDao.getAsModel(author);
    }

    public DefaultTableModel getAllAsModel(){
        return authorDao.getAllAsModel();
    }

    public Author getById(Integer author_id) {
        return authorDao.getById(author_id);
    }

}
