/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import manager.store.control.BaseStore;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import manager.entity.MessageIn;
import manager.mqtt.DBCon;

/**
 *
 * @author André Lima
 */
@Transactional(Transactional.TxType.REQUIRED)
@RequestScoped
public class MessageInStore extends BaseStore<MessageIn> {

    public List<MessageIn> all() {

        return em.createQuery("select e from MessageIn e where e.canceled = false", MessageIn.class)
                .getResultList();

    }

    public Optional<MessageIn> find(Long id) {

        MessageIn found = em.find(MessageIn.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }


    

    public MessageIn savemsg(String msg) {
        Long generatedId;
        MessageIn mg = new MessageIn();
        mg.setMessage(msg);
        mg.setRecevedat(LocalDateTime.now());
        mg.setStatus(0);

        try (Connection connection = DBCon.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO messagein (version, message, recevedat, status) VALUES (0, ?, ?, ?)";

            try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stm.setString(1, mg.getMessage());
                stm.setString(2, mg.getRecevedat().format(DateTimeFormatter.ISO_DATE_TIME));
                stm.setString(3, "0");

                int affectedRows = stm.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedId = generatedKeys.getLong(1);
                            System.out.println("ID MessageIn gerado: " + generatedId);
                            mg.setId(generatedId);
                        } else {
                            return new MessageIn();
                        }
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return new MessageIn();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageIn();
        }

        return mg;
    }

    public boolean updatemsg(MessageIn msg) {

        System.out.println("Updating message: " + msg.getId() + "to Status: " + msg.getStatus());

        try (Connection connection = DBCon.getConnection()) {
            String sql = "UPDATE messagein set "
                    + "version = version + ?, "
                    + "status = ?"
                    + "WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.CLOSE_CURRENT_RESULT)) {
                preparedStatement.setString(1, "1");
                preparedStatement.setString(2, String.valueOf(msg.getStatus()));
                preparedStatement.setString(3, String.valueOf(msg.getId()));

                int affectedRows = preparedStatement.executeUpdate();

                System.out.println("Updating realized: " + (affectedRows > 0));

                return affectedRows > 0;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

}
