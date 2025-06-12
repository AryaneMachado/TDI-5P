package model.dao;

import java.util.List;

import model.Course;
import model.ModelException;

public interface CourseDAO {
	boolean save(Course company) throws ModelException;
	boolean update(Course company) throws ModelException;
	boolean delete(Course company) throws ModelException;
	List<Course> listAll() throws ModelException;
	Course findById(int id) throws ModelException;
}
