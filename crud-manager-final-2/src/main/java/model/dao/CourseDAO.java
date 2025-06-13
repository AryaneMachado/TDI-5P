package model.dao;

import java.util.List;

import model.Course;
import model.ModelException;

/**
 * Interface que define as operações de acesso a dados (DAO) para a entidade Course.
 * Segue o padrão DAO para separação da lógica de persistência do restante da aplicação.
 */

public interface CourseDAO {

    /**
     * Salva um novo curso no banco de dados.
     * 
     * @param course Objeto Course a ser salvo.
     * @return true se o curso foi salvo com sucesso, false caso contrário.
     * @throws ModelException em caso de erro na operação de persistência.
     */
    boolean save(Course course) throws ModelException;

    /**
     * Atualiza os dados de um curso existente.
     * 
     * @param course Objeto Course com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     * @throws ModelException em caso de erro na operação.
     */
    boolean update(Course course) throws ModelException;

    /**
     * Remove um curso do banco de dados.
     * 
     * @param course Objeto Course a ser removido.
     * @return true se a remoção foi bem-sucedida, false caso contrário.
     * @throws ModelException em caso de erro na operação.
     */
    boolean delete(Course course) throws ModelException;

    /**
     * Retorna uma lista com todos os cursos cadastrados.
     * 
     * @return Lista de objetos Course.
     * @throws ModelException em caso de erro na consulta.
     */
    List<Course> listAll() throws ModelException;

    /**
     * Busca um curso pelo seu ID.
     * 
     * @param id Identificador do curso a ser buscado.
     * @return Objeto Course correspondente ao ID, ou null se não for encontrado.
     * @throws ModelException em caso de erro na consulta.
     */
    Course findById(int id) throws ModelException;
}
