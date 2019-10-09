package testingwithhsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DAO {
	private final DataSource myDataSource;
	
	public DAO(DataSource dataSource) {
		myDataSource = dataSource;
	}

	/**
	 * Renvoie le nom d'un client à partir de son ID
	 * @param id la clé du client à chercher
	 * @return le nom du client (LastName) ou null si pas trouvé
	 * @throws SQLException 
	 */
	public String nameOfCustomer(int id) throws SQLException {
		String result = null;
		
		String sql = "SELECT LastName FROM Customer WHERE ID = ?";
		try (Connection myConnection = myDataSource.getConnection(); 
		     PreparedStatement statement = myConnection.prepareStatement(sql)) {
			statement.setInt(1, id); // On fixe le 1° paramètre de la requête
			try ( ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					result = resultSet.getString("LastName");
				}
			}
		}
	
		return result;
	}
        
         public void insertProduct(product product) throws SQLException {		
		String sql = "INSERT INTO PRODUCT VALUES(?, ?, ?)";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        
			stmt.setInt(1, product.getProductId());
			stmt.setString(2, product.getName());
			stmt.setInt(3, product.getPrix());
			stmt.executeUpdate();
                }
         }
                
        public product findProduct(int productId) throws SQLException {
		product prod = null;
                String sql = "SELECT * FROM PRODUCT WHERE ID = (?)";
                try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
			PreparedStatement stmt = connection.prepareStatement(sql)
		) {
                    stmt.setInt(1, productId);

                    try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
                            String name = rs.getString("name");
                            int prix = rs.getInt("price");
                            prod = new product(productId, name, prix);
                        } 
                    }                
                } 
                
                return prod;
	}
}
	

