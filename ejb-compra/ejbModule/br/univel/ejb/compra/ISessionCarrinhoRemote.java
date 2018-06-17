package br.univel.ejb.compra;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ISessionCarrinhoRemote {
	
	void addProduto(String strProduto);
	
	List<String> getProdutos();

	void removeProduto(int codigo_produto);
}
