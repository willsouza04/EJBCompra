package br.univel.ejb.compra;

import java.util.LinkedList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

public class MainCompra {
	
	ISessionCarrinhoRemote carrinho;
	ISessionEstoqueRemote estoque;
	
	boolean fechar = false;

	public static void main(String[] args) {
		MainCompra ms = new MainCompra();
		ms.start();		
	}
	
	public void start() {
		
		try {
			InitialContext ctx =
					new InitialContext(JNDIConfig.getConfigs());
			carrinho = (ISessionCarrinhoRemote)
					ctx.lookup("ejb:/ejb-compra/SessionCarrinho!br.univel.ejb.compra.ISessionCarrinhoRemote?stateful");
			estoque = (ISessionEstoqueRemote)
					ctx.lookup("ejb:/ejb-compra/SessionEstoque!br.univel.ejb.compra.ISessionEstoqueRemote?stateful");
			
			estoque.gerarProdutos();

			do {
				String[] options = new String[]{"Gerenciar", "Comprar"};
                int option = JOptionPane.showOptionDialog(null, "Bem vindo!", null,
                        JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                        , null, options, options[1]);
                if(option == 0) {
                	Gerenciar();                	
                }
                else if(option == 1) {
                	Comprar();
                }
                else if(option == -1) {
                	fechar = true;
                }
			}while(!fechar);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		finalizar();
		
	}
	
	public void Gerenciar() {
		boolean continuar = false;
		
		do {	
			String[] options = new String[]{"Visualizar", "Adicionar", "Remover", "Voltar"};
            int option = JOptionPane.showOptionDialog(null, "Gerenciar Estoque!", null,
                    JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                    , null, options, options[0]);
            if(option == 0) {
            	VisualizarEstoque();
            }
            else if(option == 1) {
            	AdicionarEstoque();
            }
            else if(option == 2) {
            	RemoverEstoque();
            }
            else {
            	continuar = false;
            }
		}while(continuar);
		
	}
	
	public void Comprar() {
boolean continuar = false;
		
		do {	
			String[] options = new String[]{"Carrinho", "Adicionar", "Remover", "Voltar"};
            int option = JOptionPane.showOptionDialog(null, "Gerenciar Carrinho!", null,
                    JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE
                    , null, options, options[0]);
            if(option == 0) {
            	VisualizarCarrinho();
            }
            else if(option == 1) {
            	AdicionarCarrinho();
            }
            else if(option == 2) {
            	RemoverCarrinho();
            }
            else {
            	continuar = false;
            }
		}while(continuar);
	}
	
	public void VisualizarEstoque() {
		List<String[]> produtos = new LinkedList<>();		
		String strProdutos = "";
		for(String p : estoque.getProdutos()) {
			String[] produto = {p.split("#")[0], p.split("#")[1], p.split("#")[2], p.split("#")[3]};
			produtos.add(produto);
			strProdutos += p.split("#")[0] + " - " + p.split("#")[1] + " - " + p.split("#")[3] + "\n"; 
		}
		strProdutos += "\n";
		JOptionPane.showMessageDialog(null, strProdutos, "Visualizar Estoque!",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public void AdicionarEstoque() {
		List<String[]> produtos = new LinkedList<>();		
		String strProdutos = "";
		int codigo = 0;
		for(String p : estoque.getProdutos()) {
			String[] produto = {p.split("#")[0], p.split("#")[1], p.split("#")[2], p.split("#")[3]};
			produtos.add(produto);
			strProdutos += p.split("#")[0] + " - " + p.split("#")[1] + "\n"; 
		}
		strProdutos += "\n";
		boolean valida = true;
		while(valida) {
			String strCodigo = JOptionPane.showInputDialog(null, 
					"Digite o codigo do produto:\n\n" + strProdutos, "Adicionar Estoque!",
					JOptionPane.PLAIN_MESSAGE);
			if(strCodigo == null) {
				strCodigo = "-1";
			}
			codigo = Integer.parseInt(strCodigo);
			if(codigo > 0 && codigo < produtos.size()) {
				valida = false;
			}
		}		
		String strQuantidade = JOptionPane.showInputDialog(null, 
				"Digite a quantidade:\n\n", "Adicionar Estoque!",
				JOptionPane.PLAIN_MESSAGE);
		if(strQuantidade == null || Integer.parseInt(strQuantidade) < 0) {
			strQuantidade = "0";
		}
		int quantidade = Integer.parseInt(strQuantidade);
		estoque.addEstoque(codigo, quantidade);
	}
	
	public void RemoverEstoque() {
		List<String[]> produtos = new LinkedList<>();		
		String strProdutos = "";
		int codigo = 0;
		for(String p : estoque.getProdutos()) {
			String[] produto = {p.split("#")[0], p.split("#")[1], p.split("#")[2], p.split("#")[3]};
			produtos.add(produto);
			strProdutos += p.split("#")[0] + " - " + p.split("#")[1] + "\n"; 
		}
		strProdutos += "\n";
		boolean valida = true;
		while(valida) {
			String strCodigo = JOptionPane.showInputDialog(null, 
					"Digite o codigo do produto:\n\n" + strProdutos, "Remover Estoque!",
					JOptionPane.PLAIN_MESSAGE);
			if(strCodigo == null) {
				strCodigo = "-1";
			}
			codigo = Integer.parseInt(strCodigo);
			if(codigo > 0 && codigo < produtos.size()) {
				valida = false;
			}
		}		
		String strQuantidade = JOptionPane.showInputDialog(null, 
				"Digite a quantidade:\n\n", "Remover Estoque!",
				JOptionPane.PLAIN_MESSAGE);
		if(strQuantidade == null || Integer.parseInt(strQuantidade) < 0) {
			strQuantidade = "0";
		}
		int quantidade = Integer.parseInt(strQuantidade);
		estoque.removerEstoque(codigo, quantidade);
	}
	
	public void VisualizarCarrinho(){
		List<String[]> produtos = new LinkedList<>();		
		String strProdutos = "";
		double total = 0;
		if(carrinho.getProdutos() != null) {
			for(String p : carrinho.getProdutos()) {
				String[] produto = {p.split("#")[0], p.split("#")[1], p.split("#")[2], p.split("#")[3]};
				produtos.add(produto);
				strProdutos += p.split("#")[0] + " - " + p.split("#")[1] + " - " + p.split("#")[2] + " - " + p.split("#")[3] + "\n"; 
				total += (Double.parseDouble(p.split("#")[2]) * (Integer.parseInt(p.split("#")[3])));
			}
			strProdutos += "\n";
			strProdutos += "Total - " + total + "\n\n";
			String[] options = new String[]{"Finalizar", "Voltar"};
			if(!produtos.isEmpty()) {
	            int option = JOptionPane.showOptionDialog(null, strProdutos, "Visualizar Carrinho!",
	                    JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE
	                    , null, options, options[1]);
	            if(option == 0) {
	            	fechar = true;
	            }
			}
			else {
				JOptionPane.showMessageDialog(null, strProdutos, "Visualizar Carrinho!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "O carrinho esta vazio!", "Visualizar Carrinho!",
					JOptionPane.INFORMATION_MESSAGE);
		}		
	}
	
	public void AdicionarCarrinho(){
		List<String[]> produtosEstoque = new LinkedList<>();	
		List<String[]> produtosCarrinho = new LinkedList<>();
		String strProdutos = "";
		int codigo = 0;
		if(carrinho.getProdutos() != null) {
			for(String pC : carrinho.getProdutos()) {
				String[] produto = {pC.split("#")[0], pC.split("#")[1], pC.split("#")[2], pC.split("#")[3]};
				produtosCarrinho.add(produto);
			}
			for(String pE : estoque.getProdutos()) {
				boolean valida = true;
				for(String[] pC : produtosCarrinho) {
					if(pE.split("#")[0].equalsIgnoreCase(pC[0])) {
						valida = false;
						break;
					}
				}
				if(valida) {
					String[] produto = {pE.split("#")[0], pE.split("#")[1], pE.split("#")[2], pE.split("#")[3]};
					produtosEstoque.add(produto);
					strProdutos += produto[0] + " - " + produto[1] + " - " + produto[2] + " - " + produto[3] + "\n"; 
				}
				
			}
		}
		else {
			for(String p : estoque.getProdutos()) {
				String[] produto = {p.split("#")[0], p.split("#")[1], p.split("#")[2], p.split("#")[3]};
				produtosEstoque.add(produto);
				strProdutos += produto[0] + " - " + produto[1] + " - " + produto[2] + " - " + produto[3] + "\n";
			}
		}
		strProdutos += "\n";
		boolean valida = true;
		String strCodigo= "0";
		while(valida) {
			strCodigo = JOptionPane.showInputDialog(null, 
					"Digite o codigo do produto:\n\n" + strProdutos, "Adicionar no Carrinho!",
					JOptionPane.PLAIN_MESSAGE);
			if(strCodigo == null) {
				break;
			}
			codigo = Integer.parseInt(strCodigo);
			boolean aux = false;
			for(String[] p : produtosEstoque) {
				if (p[0].equalsIgnoreCase(strCodigo)) {
					aux = true;
					break;
				}
			}
			if(aux) {
				valida = false;
			}
		}
		int quantidade = 0;
		String mensagem = "Digite a quantidade:\n\n";
		while(true) {
			String strQuantidade = JOptionPane.showInputDialog(null, 
					mensagem, "Adicionar no Carrinho!",
					JOptionPane.PLAIN_MESSAGE);			
			
			if(estoque.validarEstoque(Integer.parseInt(strCodigo), Integer.parseInt(strQuantidade))) {
				if(strQuantidade == null || Integer.parseInt(strQuantidade) < 0) {
					strQuantidade = "0";
				}
				quantidade = Integer.parseInt(strQuantidade);
				break;
			}
			mensagem = "Estoque Insuficiente!\n\nDigite a quantidade:\n\n";
		}
		String srtProduto = "";
		for(String[] produto : produtosEstoque) {
			if(Integer.parseInt(produto[0]) == codigo) {
				srtProduto += produto[0] + "#" + produto[1] + "#" + produto[2] + "#" + quantidade;
				break;
			}
			
		}
		estoque.removerEstoque(codigo, quantidade);
		carrinho.addProduto(srtProduto);
	}
	
	public void RemoverCarrinho(){
		List<String[]> produtosCarrinho = new LinkedList<>();
		String strProdutos = "";
		int codigo = 0;
		int quantidade = 0;
		if(carrinho.getProdutos() != null) {
			for(String pC : carrinho.getProdutos()) {
				String[] produto = {pC.split("#")[0], pC.split("#")[1], pC.split("#")[2], pC.split("#")[3]};
				produtosCarrinho.add(produto);
				strProdutos += produto[0] + " - " + produto[1] + " - " + produto[2] + " - " + produto[3] + "\n";
			}
		}
		if(!produtosCarrinho.isEmpty()) {			
			strProdutos += "\n";
			boolean valida = true;
			while(valida) {
				String strCodigo = JOptionPane.showInputDialog(null, 
						"Digite o codigo do produto:\n\n" + strProdutos, "Remover do Carrinho!",
						JOptionPane.PLAIN_MESSAGE);
				if(strCodigo == null) {
					strCodigo = "-1";
				}
				codigo = Integer.parseInt(strCodigo);
				boolean aux = false;
				for(String[] p : produtosCarrinho) {
					if (p[0].equalsIgnoreCase(strCodigo)) {
						aux = true;
						break;
					}
				}
				if(aux) {
					valida = false;
				}
			}
			for(String[] p : produtosCarrinho) {
				if (Integer.parseInt(p[0]) == (codigo)) {
					quantidade = Integer.parseInt(p[3]);
					break;
				}
			}
			estoque.addEstoque(codigo, quantidade);
			carrinho.removeProduto(codigo);
		}
		else {
			JOptionPane.showMessageDialog(null, "Não há produtos para remover!", "Remover do Carrinho!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void finalizar() {
		JOptionPane.showMessageDialog(null, "Compra Realizada Com Sucesso!", "Finalizar Compra!",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
