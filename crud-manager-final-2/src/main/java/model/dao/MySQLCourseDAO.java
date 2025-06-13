package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.ModelException;
import model.User;


public class MySQLCourseDAO implements CourseDAO {

    @Override
    public boolean save(Course course) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "INSERT INTO courses "
                   + "(modality, name, start_date, end_date, user_id) "
                   + "VALUES (?, ?, ?, ?, ?);";

        // prepara o statement e define os parâmetros
        
        db.prepareStatement(sql);
        db.setString(1, course.getModality());
        db.setString(2, course.getName());
        db.setDate(3, new java.sql.Date(course.getStartDate().getTime()));
        db.setDate(4, new java.sql.Date(course.getEndDate().getTime()));
        db.setInt(5, course.getUser().getId());

        // chama comando
        
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Course course) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "UPDATE courses SET "
                   + "modality = ?, "
                   + "name = ?, "
                   + "start_date = ?, "
                   + "end_date = ?, "
                   + "user_id = ? "
                   + "WHERE id = ?;";

        db.prepareStatement(sql);
        db.setString(1, course.getModality());
        db.setString(2, course.getName());
        db.setDate(3, new java.sql.Date(course.getStartDate().getTime()));
        db.setDate(4, new java.sql.Date(course.getEndDate().getTime()));
        db.setInt(5, course.getUser().getId());
        db.setInt(6, course.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Course course) throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "DELETE FROM courses WHERE id = ?;";
        db.prepareStatement(sql);
        db.setInt(1, course.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Course> listAll() throws ModelException {
        DBHandler db = new DBHandler();

        String sql = "SELECT c.*, u.id AS user_id "
                   + "FROM courses c "
                   + "JOIN users u ON c.user_id = u.id "
                   + "ORDER BY c.name;";

        db.createStatement();
        db.executeQuery(sql);

        List<Course> list = new ArrayList<>();
        
        while (db.next()) {
            list.add(createCourse(db));
        }

        return list;
    }

    @Override
    public Course findById(int id) throws ModelException {
        DBHandler db = new DBHandler();

        // busca por id
        
        String sql = "SELECT * FROM courses WHERE id = ?;";
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        if (db.next()) {
            return createCourse(db);
        }

        return null;
    }


    private Course createCourse(DBHandler db) throws ModelException {
        Course c = new Course(db.getInt("id")); // cria curso
        c.setModality(db.getString("modality"));
        c.setName(db.getString("name"));
        c.setStartDate(db.getDate("start_date"));
        c.setEndDate(db.getDate("end_date"));

        // relacionamento
        
        UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
        User u = userDAO.findById(db.getInt("user_id"));
        c.setUser(u);

        return c;
    }
}
