package br.univel.ejb.compra;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ISessionEstoqueRemote {
	
	void gerarProdutos();
	
	List<String> getProdutos();
	
	boolean validarEstoque(int codigo_produto, int quantidade);
	
	void addEstoque(int codigo_produto, int quantidade);
	
	void removerEstoque(int codigo_produto, int quantidade);
}
