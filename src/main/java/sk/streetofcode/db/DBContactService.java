package sk.streetofcode.db;

import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBContactService {
    private static final String READ_ALL = "SELECT * FROM contact";
    private static final String CREATE = "INSERT INTO contact (name, email, phone) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM contact WHERE id = ?";
    private static final String EDIT = "UPDATE contact SET name = ?, email = ?, phone = ? WHERE id = ?";

    private static final String SEARCH_BY_EMAIL = "SELECT * FROM contact WHERE email LIKE ?";

    private static final Logger logger = getLogger(DBContactService.class);

    public List<Contact> readAll() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
            return contacts;
        } catch (SQLException e) {
            logger.error("Error while reading all contacts!", e);
            return null;
        }
    }

    public int delete(int id) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setInt(1, id);
            // returns number of affected rows
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting contact!", e);
            return 0;
        }
    }

    public int edit(Contact contact) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EDIT)) {

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getId());
            // returns number of affected rows
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Contact with this email or phone already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while editing contact!", e);
            return 0;
        }
    }

    public int create(String name, String email, String phone) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);

            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Contact with this email or phone already exists");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while creating contact!", e);
            return 0;
        }
    }

    public List<Contact> searchByEmail(String email) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BY_EMAIL)) {

            statement.setString(1, "%" + email + "%");
            ResultSet resultSet = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
            return contacts;
        } catch (SQLException e) {
            logger.error("Error while searching by email!", e);
            return null;
        }
    }
}
