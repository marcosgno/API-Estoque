package com.exemplo.sping.estoque.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.sping.estoque.config.ResourceNotFoundException;
import com.exemplo.sping.estoque.models.Estoque;
import com.exemplo.sping.estoque.models.Produto;
import com.exemplo.sping.estoque.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping(value="/api")
@Api(value="API REST Loja Virtual - PRODUTOS")
public class ProdutoResources {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping("/produtos")
	@ApiOperation("Lista todos os produtos cadastrados.")
	@ResponseBody
	public List<Produto> listarProdutos(){
		return produtoRepository.findAll();
	}
	
	@PostMapping("/produto")
	@ApiOperation("Cadastra um novo produto.")
	public Produto salvarProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto); 
	}
	
	@PutMapping("/produto")
	@ApiOperation("Atualiza um produto cadastrado.")
	public Produto atualizarProduto(@RequestBody Produto produto) {
		Optional<Produto> produtoCadastrado = produtoRepository.findById(produto.getId());

		if (produtoCadastrado.isPresent()) {
			return produtoRepository.save(produto);
		} else
			throw new ResourceNotFoundException("Produto informado não encontrado.");		
	}	
	
	@DeleteMapping("/produto/{idProduto}")
	@ApiOperation("Remove um produto cadastrado.")
	public void removerProduto(@PathVariable(value = "idProduto") int idProduto) {
		Optional<Produto> produto = produtoRepository.findById(idProduto);

		if (produto.isPresent()) {
			produtoRepository.delete(produto.get());
		} else
			throw new ResourceNotFoundException("Produto informado não encontrado");		 
	}		

}
