package br.univel.ejb.compra;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;

import br.univel.ejb.compra.model.Produto;

@Stateful
public class SessionEstoque implements ISessionEstoqueRemote {
	
	List<Produto> produtos = new LinkedList<>();		

	public void gerarProdutos() {
		Produto produto1 = new Produto(1, "Tv", 1069, 12);
		Produto produto2 = new Produto(2, "Smartphone", 899.00, 48);
		Produto produto3 = new Produto(3, "Freezer", 1600.00, 16);
		Produto produto4 = new Produto(4, "Parafusadeira", 138.00, 12);
		Produto produto5 = new Produto(5, "Notebook", 3299.00, 15);
		Produto produto6 = new Produto(6, "Cerveja", 8.00, 68);
		
		
		produtos.add(produto1);
		produtos.add(produto2);
		produtos.add(produto3);
		produtos.add(produto4);
		produtos.add(produto5);
		produtos.add(produto6);		
	}

	@Override
	public boolean validarEstoque(int codigo_produto, int quantidade) {
		for(Produto produto : produtos) {
			if(produto.getCodigo() == codigo_produto) {
				if(produto.getEstoque() >= quantidade) {
					return true;
				}
			}
		}		
		return false;
	}

	@Override
	public void addEstoque(int codigo_produto, int quantidade) {
		for(Produto produto : produtos) {
			if(produto.getCodigo() == codigo_produto) {
				produto.setEstoque(produto.getEstoque() + quantidade);
			}
		}						
	}

	@Override
	public void removerEstoque(int codigo_produto, int quantidade) {
		for(Produto produto : produtos) {
			if(produto.getCodigo() == codigo_produto) {
				if(produto.getEstoque() >= quantidade) {
					produto.setEstoque(produto.getEstoque() - quantidade);
				}
				else {
					produto.setEstoque(0);
				}
			}
		}
	}

	@Override
	public List<String> getProdutos() {	
		List<String> srtProdutos = new LinkedList<>();
		for(Produto produto : produtos) {
			srtProdutos.add(""+produto.getCodigo()+"#"+
							produto.getNome()+"#"+
							produto.getPreco()+"#"+
							produto.getEstoque());
		}
		return srtProdutos;
	}

}
