package br.com.coldigogeladeiras.jdbcinterface;

import java.util.List;

import br.com.coldigogeladeiras.modelo.Marca;

public interface MarcaDAO {
	
	public List<Marca> buscar(String nomeMarca);
	  public boolean inserirMarca(Marca marca);
	  public boolean deletar(int id);
	  public Marca buscarPorId(int id);
	  public boolean alterar(Marca marca);
	  public boolean ativaDesativa(Marca marca);
	  }
