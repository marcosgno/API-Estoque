package com.exemplo.sping.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.sping.estoque.models.Produto;



public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
