package sk.streetofcode;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String select = "SELECT * FROM contact";
        String connectionUrl = "jdbc:mysql://localhost:3306/contacts";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password");
             PreparedStatement ps = conn.prepareStatement(select);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone");

                System.out.println("id: " + id + ", name: " + name + ", email: " + email + ", phone number: " + phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database!");
        }
    }
}
