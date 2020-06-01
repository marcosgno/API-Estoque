package com.exemplo.sping.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.sping.estoque.models.Estoque;


public interface EstoqueRepository extends JpaRepository<Estoque, Integer>{
	
	public Estoque findByprodutoId(int idProduto); 
 
}
