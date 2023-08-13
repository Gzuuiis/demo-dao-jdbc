package application;

import java.util.Date;
import java.util.List;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); // assim o programa não conhece a implementação, apenas a interface
		// Assim geramos uma injeção de depedência sem expor a implementação
		
		
		System.out.println("==== FindByDepartment =============");
		
		Department department = new Department(3, null);
		List<Seller> list = sellerDao.findDepartment(department);
		for (Seller obj2 : list) {
			System.out.println(obj2);
		}
		
		
		System.out.println("==== FindAll =============");
		
		department = new Department(3, null);
		List<Seller> list2 = sellerDao.findAll();
		
		for (Seller obj2 : list2) {
			System.out.println(obj2);
		}
		
		
		System.out.println("==== INSERT =============");
		
		Seller newSeller = new Seller(0, "Payet", "payet10@vasco.com", new Date(), 3000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserido! Novo id = " + newSeller.getId());

	} 	

}