package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.SellerDao;
import db.DB;
import entities.Department;
import entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
			
			while(rs.next()) {
				
				Department dep = instatiateDepartment(rs);
				Seller obj = instatiateSeller(rs, dep);
				
				return obj;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return null;
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department(
				rs.getInt("DepartmentId"),
				rs.getString("DepName"));
		
		return dep;
	}
	
	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
