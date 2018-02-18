//package ir.maktab.UserInterFace;
//
//import ir.maktab.DAO.Prof.ProfDAO;
//import ir.maktab.DAO.Prof.ProfDAOImpl;
//import ir.maktab.DAO.Student.StudentDAO;
//import ir.maktab.DAO.Student.StudentDAOImpl;
//import ir.maktab.models.Prof;
//import ir.maktab.models.Student;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * Created by nader on 11/18/2017.
// */
//public class CLI {
//    static Scanner in = new Scanner(System.in);
//    StudentDAO studentDAO = new StudentDAOImpl();
//    ProfDAO profDAO = new ProfDAOImpl() ;
//
//    private static void print(Object o) {
//        System.out.println(o);
//    }
//
//    private void print(List<? extends Object> objects) {
//        objects.forEach(CLI::print);
//    }
//
//    private String getInput() {
//        return in.next();
//    }
//
//    private void addProfessor() throws SQLException{
//        print("Name:");
//        String profName = getInput();
//        print("Address:");
//        String profAddress = getInput();
//        int exist = profDAO.searchByName(profName);
//        if (exist==0) {
//            Prof professor = new Prof(profName, profAddress);
//            profDAO.add(professor);
//        }
//        else
//            print("name exist! id: "+exist);
//
//    }
//
//    private void addStudent() throws SQLException{
//        print("Name:");
//        String studentName = getInput();
//        print("Dept:");
//        String studentDept = getInput();
//        print("SupervisorID:");
//        int studentId = in.nextInt();
//        int exist = studentDAO.searchByName(studentName);
//        if (exist==0) {
//            Student s = new Student(studentName, studentDept, studentId);
//            studentDAO.add(s);
//        }
//        else
//            print("name exist! id: "+exist);
//    }
//
//    private void deleteProf() throws SQLException{
//        print("id:");
//        int id = in.nextInt();
//        profDAO.delete(id);
//    }
//
//    private void deleteStudent() throws SQLException{
//        print("id:");
//        int stId = in.nextInt();
//        StudentDAOImpl.choose();
//        studentDAO.delete(stId);
//    }
//
//    private void deleteStudentByName() throws SQLException{
//        print("name:");
//        String stName = getInput();
//        StudentDAOImpl.choose();
//        studentDAO.delete(stName);
//    }
//
//    private void showStudentSupervisor() throws SQLException{
//        print("student name:");
//        String name = getInput();
//        StudentDAOImpl.choose();
//        print(studentDAO.showSupervisor(studentDAO.searchByName(name)).getName());
//    }
//
//    public void mainMenu() {
//        System.out.println("1- add Professor\n" +
//                "2- add Student\n" +
//                "3- show Student's Supervisor\n" +
//                "4- delete Student\n" +
//                "\n0- exit\n");
//
//        makeDecision();
//    }
//
//    private void makeDecision() {
//        try {
//
//            while (true) {
//                while (true) {
//                    System.out.print("Enter Request Number:");
//                    int req = in.nextInt();
//                    switch (req) {
//                        case 1:
//                            addProfessor();
//                            break;
//
//                        case 2:
//                            addStudent();
//                            break;
//
//                        case 3:
//                            showStudentSupervisor();
//                            break;
//
//                        case 4:
//                            deleteStudentByName();
//                            break;
//
//                        case 0:
//                            System.exit(1);
//                            break;
//
//                        default:
//                            System.out.println("wrong!");
//                            break;
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
