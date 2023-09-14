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
import manager.store.control.BaseStore;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import manager.entity.TagDiscoveryLog;
import manager.mqtt.DBCon;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TagDiscoveryLogStore extends BaseStore<TagDiscoveryLog> {

    public List<TagDiscoveryLog> all() {

        return em.createQuery("select e from TagDiscoveryLog e where e.canceled = false", TagDiscoveryLog.class)
                .getResultList();

    }

    public Optional<TagDiscoveryLog> find(Long id) {

        TagDiscoveryLog found = em.find(TagDiscoveryLog.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }


    @Override
    public TagDiscoveryLog save(TagDiscoveryLog obj) {
        Long generatedId;
        TagDiscoveryLog td = new TagDiscoveryLog();
        
        try (Connection connection = DBCon.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO tagdiscoverylog (version, tag_id, status, status_use, msg_id) VALUES (0, ?, ?, ?, ?)";

            try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stm.setString(1, obj.getTag().getId().toString());
                stm.setString(2, String.valueOf(obj.getStatus()));
                stm.setString(3, obj.getStatus_use());
                stm.setString(4, obj.getMsg().getId().toString());

                int affectedRows = stm.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedId = generatedKeys.getLong(1);
                            System.out.println("ID TagLog gerado: " + generatedId);
                            td.setId(generatedId);
                        } 
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return td;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return td;
        }

        return td;
    }

}
