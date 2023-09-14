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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import manager.entity.Controller;
import manager.entity.MessageOut;
import manager.entity.Publish;
import manager.entity.Tag;
import manager.entity.TagDiscoveryLog;
import manager.mqtt.DBCon;
import manager.mqtt.constant.TopicPublisher;
import manager.mqtt.mapping.MessagetoJson;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class PublishStore extends BaseStore<Publish> {

    public List<Publish> all() {

        return em.createQuery("select e from Publish e where e.canceled = false", Publish.class)
                .getResultList();

    }

    public Optional<Publish> find(Long id) {

        Publish found = em.find(Publish.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }

    public MessageOut updateController(Controller cr) {
        Publish pl = new Publish(LocalDateTime.now());
        pl = save(pl);
        MessagetoJson msg = new MessagetoJson(pl, null, cr, null, TopicPublisher.ControllerConfig);

        return new MessageOut(msg.getJsPublish().toJSONString(), pl);
    }

    public MessageOut updateTag(List<Tag> tags, Controller cr) {
        Publish pl = new Publish(LocalDateTime.now());
        pl = save(pl);
        MessagetoJson msg = new MessagetoJson(pl, tags, cr, null, TopicPublisher.TagConfig);

        return new MessageOut(msg.getJsPublish().toJSONString(), pl);
    }

    public MessageOut updateTag(Tag tag, Controller cr) {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        return updateTag(tags, cr);
    }

    public Publish findPublish(String id) {

        Publish pl = new Publish();

        try (Connection connection = DBCon.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "select * from publishlog where id = ?";

            try (PreparedStatement stm = connection.prepareStatement(sql, Statement.CLOSE_CURRENT_RESULT)) {
                stm.setString(1, id);

                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        pl.setId(rs.getLong("id"));
                        pl.setVersion(rs.getLong("version"));
                        //pl.publishdate(rs.getString("publishdate"));
                        //pl.setActivation(rs.getTimestamp("activation").toLocalDateTime());

                    } else {
                        System.out.println("Tag received doesn't exist: " + id);
                        throw new Exception("Publish not exists."); 
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return pl;
    }

    
    public boolean updateByMqtt(Publish obj) {

        
        try (Connection connection = DBCon.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "UPDATE publishlog set version = version + 1, "
                    + "status = ? "
                    + "where id = ?";

            try (PreparedStatement stm = connection.prepareStatement(sql, Statement.CLOSE_CURRENT_RESULT)) {
                stm.setString(1, String.valueOf(obj.getStatus()));
                stm.setString(2, String.valueOf(obj.getId()));

                int affectedRows = stm.executeUpdate();

                if (affectedRows > 0) {
                    
                    connection.commit();
                    return true;
                }
    
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
       
        return false;
    }


}
