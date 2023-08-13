package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.SellerDao;
import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void insert(Seller obj) {
       
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         
         try {
             ps = conn.prepareStatement(
             "INSERT INTO seller " +
             "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
             "VALUES " +
             "(?, ?, ?, ?, ?)",
             Statement.RETURN_GENERATED_KEYS);
             
             ps.setString(1, obj.getName());
             ps.setString(2, obj.getEmail());
             ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
             ps.setDouble(4, obj.getBaseSalary());
             ps.setInt(5, obj.getDepartment().getId());
             
             int rowsAffected = ps.executeUpdate();
             
             if(rowsAffected > 0) {
            	 rs = ps.getGeneratedKeys();
            	 if(rs.next()) {
            		 int id = rs.getInt(1);
            		 obj.setId(id);
            	 }
            }
             else {
            	 throw new DbException("Erro Inesperado! Nenhuma linha afetada");
             }
             DB.closeResultSet(rs);
         } 
         catch (SQLException e) {
             throw new RuntimeException("Erro ao inserir vendedor " + e.getMessage());
         } finally {
             DB.closeStatement(ps);
         }
    }

    public void update(Seller obj) {
        // TODO: Implementar a lógica de atualização
    }

    public void deleteById(Integer id) {
        // TODO: Implementar a lógica de exclusão
    }

    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.id " +
                "WHERE seller.id = ?");
            
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendedor por ID: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
        return null;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department(
            rs.getInt("DepartmentId"),
            rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller(
            rs.getInt("Id"), 
            rs.getString("Name"), 
            rs.getString("Email"),
            rs.getDate("BirthDate"),
            rs.getDouble("BaseSalary"),
            dep);
        return obj;
    }

    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.id " +
                "ORDER BY seller.Id");
            
            rs = ps.executeQuery();
            
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os vendedores: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    public List<Seller> findDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.id " +
                "WHERE seller.DepartmentId = ? " + 
                "ORDER BY seller.Name");
            
            ps.setInt(1, department.getId());
            
            rs = ps.executeQuery();
            
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendedores por departamento: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
