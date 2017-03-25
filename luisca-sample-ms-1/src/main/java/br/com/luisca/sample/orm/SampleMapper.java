package br.com.luisca.sample.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.luisca.sample.domain.Sample;
import br.com.luisca.sample.exception.ApiException;

public class SampleMapper {
	
	public Sample select(Sample Sample) throws ApiException {
		Connection conexao = null;
		PreparedStatement statement = null;
		try {
			 conexao = Database.getConnection();
			 statement = conexao.prepareStatement(
					"SELECT * FROM Sample WHERE ID = ?");
			statement.setLong(1, Sample.getId());
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				Sample = new Sample(
						rs.getInt("ID"), 
						rs.getString("NOME"));
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new ApiException(500, e.getMessage());
		}finally {
			try {
				if(statement!=null){
					statement.close();
				}
				if(conexao!=null&&!conexao.isClosed()){
					conexao.close();
				}
				
			} catch (SQLException e) {
				throw new ApiException(500, e.getMessage());
			}
		}

		return Sample;
	}

	public Sample insert(Sample Sample) throws ApiException {
		Connection conexao = null;
		PreparedStatement statement = null;
		try {
			conexao = Database.getConnection();
			statement = conexao.prepareStatement(
					"INSERT INTO Sample(NOME) VALUES(?)", 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, Sample.getNome());
			statement.execute();

			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				Sample.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			throw new ApiException(500, e.getMessage());
		} finally {
			try {
				if(statement!=null){
					statement.close();
				}
				if(conexao!=null&&!conexao.isClosed()){
					conexao.close();
				}
				
			} catch (SQLException e) {
				throw new ApiException(500, e.getMessage());
			}
		}

		return this.select(Sample);
	}

	public Sample update(Sample Sample) throws ApiException {

		try {
			Connection conexao = Database.getConnection();
			PreparedStatement statement = conexao
					.prepareStatement(
							"UPDATE Sample SET NOME = ? WHERE ID = ?");
			statement.setString(1, Sample.getNome());
			statement.setLong(2, Sample.getId());
			statement.execute();

			if (statement.getUpdateCount() == 0) {
				throw new ApiException(404, "O Sample informado não existe.");
			}
		} catch (ApiException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApiException(500, "Erro não especificado: " + e.getMessage());
		}

		return this.select(Sample);
	}

	public Sample delete(Sample Sample) throws ApiException {
		try {
			Connection conexao = Database.getConnection();
			PreparedStatement statement = conexao.prepareStatement(
					"DELETE FROM Sample WHERE ID = ?");
			statement.setLong(1, Sample.getId());
			statement.execute();

			if (statement.getUpdateCount() == 0) {
				throw new ApiException(404, "O Sample informado não existe.");
			}
		} catch (ApiException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApiException(500, "Erro não especificado: " + e.getMessage());
		}

		return null;
	}

}
