/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import manager.store.control.BaseStore;
import manager.entity.Tag;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import manager.mqtt.DBCon;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TagStore extends BaseStore<Tag> {

    public List<Tag> all() {

        return em.createQuery("select e from Tag e where e.canceled = false", Tag.class)
                .getResultList();

    }

    public Tag find(String address) {

        return em.createQuery("select e from Tag e where e.canceled = false AND e.address = :address", Tag.class)
                .setParameter("address", address)
                .getSingleResult();

    }

    public Optional<Tag> find(Long id) {

        Tag found = em.find(Tag.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }

    @Override
    public Tag save(Tag obj) {
        obj.setStatus(1);
        return super.save(obj);
    }

    public Tag findTag(String address) {

        Tag tag = new Tag();

        try (Connection connection = DBCon.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "select * from tag where address = ?";

            try (PreparedStatement stm = connection.prepareStatement(sql, Statement.CLOSE_CURRENT_RESULT)) {
                stm.setString(1, address);

                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        tag.setId(rs.getLong("id"));
                        tag.setAddress(rs.getString("address"));
                        tag.setName(rs.getString("name"));
                        tag.setActivation(rs.getTimestamp("activation").toLocalDateTime());
                        tag.setStatus(rs.getInt("status"));
                        tag.setStatus_use(rs.getInt("status_use"));

                    } else {
                        System.out.println("Tag received doesn't exist: " + address);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return tag;
    }

    
}
