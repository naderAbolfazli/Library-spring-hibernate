package ir.maktab;

import ir.maktab.UserInterFace.MainGUI;
import ir.maktab.model.author.Author;
import ir.maktab.model.author.manager.AuthorManager;
import ir.maktab.model.book.Book;
import ir.maktab.model.book.manager.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by Nader on 2/5/2018.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigAll.class);

        ctx.getBean(MainGUI.class).run();


    }
}
