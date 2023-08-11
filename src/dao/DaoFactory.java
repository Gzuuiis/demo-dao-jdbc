package dao;

import dao.impl.SellerDaoJDBC;
import db.DB;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	// ------------- OQUE É DAO? --------------
	
	//O PADRÃO DE PROJETO DAO SURGIU COM A NECESSIDADE DE SEPARARMOS A LÓGICA DE NEGÓCIOS DA LÓGICA DE PERSISTÊNCIA DE DADOS.
	//ESTE PADRÃO PERMITE QUE POSSAMOS MUDAR A FORMA DE PERSISTÊNCIA SEM QUE ISSO INFLUENCIE EM NADA NA LÓGICA DE NEGÓCIO, 
	//ALÉM DE TORNAR NOSSAS CLASSES MAIS LEGÍVEIS.
	
}
