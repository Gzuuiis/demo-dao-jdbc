package application;

import java.util.Date;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		System.out.println(obj);
		
		Seller seller = new Seller(1, "Robert", "robert@gmail.com", new Date(), 3000.0, obj);
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); // assim o programa não conhece a implementação, apenas a interface
		// Assim geramos uma injeção de depedência sem expor a implementação
		
		System.out.println(seller);

	} 	

}
