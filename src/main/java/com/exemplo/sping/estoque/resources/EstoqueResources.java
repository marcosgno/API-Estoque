package com.exemplo.sping.estoque.resources;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.sping.estoque.config.ResourceNotFoundException;
import com.exemplo.sping.estoque.models.Estoque;
import com.exemplo.sping.estoque.models.Produto;
import com.exemplo.sping.estoque.repository.EstoqueRepository;
import com.exemplo.sping.estoque.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Loja Virtual - ESTOQUE")
public class EstoqueResources {

	@Autowired
	EstoqueRepository estoqueRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping("/estoque")
	@ApiOperation("Lista todos os produtos em estoque.")
	public List<Estoque> listarProdutos() {
		return estoqueRepository.findAll();
	}

	@PostMapping("/estoque/{idProduto}/{quantidade}")
	@ApiOperation("Cadastra estoque de um produto.")
	public Estoque salvarProduto(@PathVariable(value = "idProduto") int idProduto,
			@PathVariable(value = "quantidade") double quantidade) {

		Optional<Produto> produto = produtoRepository.findById(idProduto);

		if (produto.isPresent()) {
			Optional<Estoque> estoque = Optional.ofNullable(estoqueRepository.findByprodutoId(idProduto));
			if (estoque.isPresent()) {
				throw new RuntimeException("Produto informado já cadastrado no estoque.");
			} 
			return estoqueRepository.save(new Estoque(produto.get(), quantidade));
		} else
			throw new ResourceNotFoundException("Produto informado não encontrado");
	}

	@PutMapping("/estoque/{idProduto}/{quantidade}")
	@ApiOperation("Atualiza o estoque de um produto.")
	public Estoque atualizarProduto(@PathVariable(value = "idProduto") int idProduto,
			@PathVariable(value = "quantidade") double quantidade) {
		Optional<Estoque> estoque = Optional.ofNullable(estoqueRepository.findByprodutoId(idProduto));
		if (estoque.isPresent()) {
			estoque.get().setQuantidade(quantidade);
			return estoqueRepository.saveAndFlush(estoque.get());
		} else
			throw new ResourceNotFoundException("Estoque não encontrado para produto informado.");
	}

	@DeleteMapping("/estoque/{idProduto}")
	@ApiOperation("Remove o estoque do produto.")
	public void removerProduto(@PathVariable(value = "idProduto") int idProduto) {
		Optional<Estoque> estoque = Optional.ofNullable(estoqueRepository.findByprodutoId(idProduto));
		if (estoque.isPresent()) {
			estoqueRepository.delete(estoque.get());
		} else
			throw new ResourceNotFoundException("Estoque não encontrado para produto informado");
	}

}
