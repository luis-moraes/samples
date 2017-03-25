package br.com.luisca.sample.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.luisca.sample.exception.ApiException;
import br.com.luisca.sample.orm.Database;

public class SampleRepository {
	public static final int PAGE_LENGTH = 5;
	public List<Sample> getByRange(int page) throws ApiException {

		List<Sample> lista = new ArrayList<Sample>();

		try {
			Connection conexao = Database.getConnection();
			
			page = (page - 1) * PAGE_LENGTH;
			
			PreparedStatement statement = conexao.prepareStatement(
					"SELECT * FROM Sample LIMIT ?,?");
			statement.setInt(1, page);
			statement.setInt(2, PAGE_LENGTH);
			
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Sample sample = new Sample(
						rs.getInt("ID"), 
						rs.getString("NOME"));
				lista.add(sample);
			}
		} catch(Exception e){
			throw new ApiException(500, 
					"Erro ao paginar os dados do banco de dados. " + e.getMessage());
		}			

		return lista;
	}

	public List<Sample> getAll() throws ApiException {

		List<Sample> lista = new ArrayList<Sample>();

		try {
			Connection conexao = Database.getConnection();
			PreparedStatement statement = conexao.prepareStatement(
					"SELECT * FROM Sample LIMIT " + PAGE_LENGTH);

			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				Sample Sample = new Sample(
						rs.getInt("ID"), 
						rs.getString("NOME"));
				lista.add(Sample);
			}
		} catch(Exception e){
			throw new ApiException(500, 
					"Erro ao buscar os dados no banco de dados. " + e.getMessage());
		}

		return lista;
	}

}
