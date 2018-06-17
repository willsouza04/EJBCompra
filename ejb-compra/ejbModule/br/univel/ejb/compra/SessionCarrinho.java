package br.univel.ejb.compra;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;

import br.univel.ejb.compra.model.Produto;

@Stateful
public class SessionCarrinho implements ISessionCarrinhoRemote{
	
	public List<Produto> produtos = new LinkedList<>();

	@Override
	public void addProduto(String strProduto) {
		Produto produto = new Produto(Integer.parseInt(strProduto.split("#")[0]), 
						strProduto.split("#")[1], 
						Double.parseDouble(strProduto.split("#")[2]), 
						Integer.parseInt(strProduto.split("#")[3]));
		produtos.add(produto);
	}

	@Override
	public List<String> getProdutos() {
		List<String> srtProdutos = new LinkedList<>();
		if(produtos != null) {
			for(Produto produto : produtos) {
				srtProdutos.add(produto.getCodigo()+"#"+
								produto.getNome()+"#"+
								produto.getPreco()+"#"+
								produto.getEstoque());
			}
			return srtProdutos;
		}
		return null;
	}

	@Override
	public void removeProduto(int codigo_produto) {	
		for(Produto produto : produtos) {
			if(produto.getCodigo() == codigo_produto) {
				produtos.remove(produto);
			}
		}		
	}	
	
}
