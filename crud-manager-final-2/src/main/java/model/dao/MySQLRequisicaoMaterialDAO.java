package model.dao;

import java.sql.*;
import java.util.*;
import model.*;

public class MySQLRequisicaoMaterialDAO implements RequisicaoMaterialDAO {
    private Connection conn;

    public MySQLRequisicaoMaterialDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(RequisicaoMaterial obj) {
        String sql = "INSERT INTO requisicao_material (descricao, quantidade, data_requisicao, status, usuario_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getDescricao());
            st.setInt(2, obj.getQuantidade());
            st.setDate(3, new java.sql.Date(obj.getDataRequisicao().getTime()));
            st.setString(4, obj.getStatus());
            st.setInt(5, obj.getUsuario().getId());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) obj.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(RequisicaoMaterial obj) {
        String sql = "UPDATE requisicao_material SET descricao=?, quantidade=?, data_requisicao=?, status=?, usuario_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getDescricao());
            st.setInt(2, obj.getQuantidade());
            st.setDate(3, new java.sql.Date(obj.getDataRequisicao().getTime()));
            st.setString(4, obj.getStatus());
            st.setInt(5, obj.getUsuario().getId());
            st.setInt(6, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM requisicao_material WHERE id=?")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RequisicaoMaterial findById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement(
            "SELECT * FROM requisicao_material rm JOIN users u ON rm.usuario_id = u.id WHERE rm.id=?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                RequisicaoMaterial r = instantiate(rs);
                return r;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RequisicaoMaterial> findAll() {
        List<RequisicaoMaterial> list = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(
                "SELECT * FROM requisicao_material rm JOIN users u ON rm.usuario_id = u.id");
            while (rs.next()) {
                list.add(instantiate(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private RequisicaoMaterial instantiate(ResultSet rs) throws SQLException {
        RequisicaoMaterial r = new RequisicaoMaterial();
        User u = new User();
        r.setId(rs.getInt("id"));
        r.setDescricao(rs.getString("descricao"));
        r.setQuantidade(rs.getInt("quantidade"));
        r.setDataRequisicao(rs.getDate("data_requisicao"));
        r.setStatus(rs.getString("status"));
        u.setId(rs.getInt("usuario_id"));
        u.setName(rs.getString("name")); // caso deseje mostrar nome
        r.setUsuario(u);
        return r;
    }
}
