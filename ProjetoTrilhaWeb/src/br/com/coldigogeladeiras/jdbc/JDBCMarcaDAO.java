package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import br.com.coldigogeladeiras.jdbcinterface.MarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;

public class JDBCMarcaDAO implements MarcaDAO {
	
	private Connection conexao;
	
	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public List<Marca> buscar(String nomeMarca) {
		
			//criação da instrução SQL para busca de todas as marcas
		String comando = "SELECT * FROM marcas";
		
		if(!nomeMarca.equals("")) {
			comando += " WHERE nome LIKE '%" + nomeMarca + "%'";
		}
		comando += " ORDER BY nome ASC";
			//Criação de uma lista para armazenar cada marca encontrada
		List<Marca> listMarcas = new ArrayList<Marca>();
		
			//Criação do objeto marca com valor null (ou seja, sem instanciá-lo)
		Marca marca = null;
		
			//Abertura do try-catch
		try {
			//Uso da conexão do banco para prepara-lo para uma instrução SQL
			Statement stmt = conexao.createStatement();
			
			//Execução da instrução criada previamente
			//e armazenamento do resultado no objeto rs
			ResultSet rs = stmt.executeQuery(comando);
			
			//Equanto houver uma proxima linha no resultado
			while (rs.next()) {
				//Criação de instancia da classe marca
				marca = new Marca();
				
				//recebimento dos 2 dados retornados do BD para cada linha econtrada
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				int status = rs.getInt("status");
				//setado no objeto marca os valores encontrados
				marca.setId(id);
				marca.setNome(nome);
				marca.setStatus(status);
				
				//adição da instancia contida no objeto marca na lista de marcas
				
				listMarcas.add(marca);
			}
			
			
			//caso alguma Exception seja gerada no try, recebe-a no objeto "ex"	
		}catch (Exception ex) {
			//Exibe a exceção na console
			ex.printStackTrace();
		}
		
			//Retorna para quem chamou o método lista criada
		return listMarcas;
	}
	
	public boolean inserirMarca(Marca marca) {
		String comando = "SELECT COUNT(*) FROM marcas WHERE nome = ?";
	    PreparedStatement consulta;
	    try {
	        consulta = this.conexao.prepareStatement(comando);
	        consulta.setString(1, marca.getNome());

	        // Executa a consulta
	        ResultSet resultado = consulta.executeQuery();

	        // Se o resultado contiver alguma linha, isso significa que a marca com o mesmo nome já existe
	        if (resultado.next()) {
	            int count = resultado.getInt(1);
	            if (count > 0) {
	                // Marca com o mesmo nome já existe, não é possível inserir novamente
	                return false;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		 comando = "INSERT INTO marcas "
				+ "(id, nome)"
				+ " VALUES (?,?)";
		
		PreparedStatement p;
		try {
			p=this.conexao.prepareStatement(comando);
			p.setInt(1, marca.getId());
			p.setString(2, marca.getNome());
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deletar(int id) {
		String comando = "DELETE FROM marcas WHERE id = ?";
		PreparedStatement p;
		try {
			p=this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Marca buscarPorId(int id) {
		String comando = "SELECT * FROM marcas WHERE marcas.id = ?";
		Marca marca = new Marca();
		try {
			PreparedStatement p= this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				String nome = rs.getString("nome");
				marca.setId(id);
				marca.setNome(nome);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return marca;
	}
	
	public boolean alterar(Marca marca) {
		String comando = "UPDATE marcas "
				+ "SET nome=?"
				+ "WHERE id=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, marca.getNome());
			p.setInt(2, marca.getId());
			
			p.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean ativaDesativa(Marca marca) {
			int num;
	
			if(marca.getStatus() == 1 ) {
				num = 0;
			}else {
				num = 1;
			}
			String comando = "UPDATE marcas "
						+ " SET status=?"
						+ " WHERE id=?";
			PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, num);
			p.setInt(2, marca.getId());
			
			p.executeUpdate();			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}


