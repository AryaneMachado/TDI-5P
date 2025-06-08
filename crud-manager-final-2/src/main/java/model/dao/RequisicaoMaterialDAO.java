package model.dao;

import java.util.List;
import model.RequisicaoMaterial;

public interface RequisicaoMaterialDAO {
    void insert(RequisicaoMaterial obj);
    void update(RequisicaoMaterial obj);
    void deleteById(Integer id);
    RequisicaoMaterial findById(Integer id);
    List<RequisicaoMaterial> findAll();
}