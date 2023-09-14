/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import manager.store.control.BaseStore;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import manager.entity.Asset;
import manager.entity.Tag;
import manager.entity.AssetTagLog;
import java.util.ArrayList;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AssetStore extends BaseStore<Asset> {

    List<Tag> tags = new ArrayList<>();

    @Inject
    private AssetTagLogStore s_assetlog;

    public List<Asset> all() {

        return em.createQuery("select e from Asset e where e.canceled = false", Asset.class)
                .getResultList();

    }

    public Optional<Asset> find(Long id) {

        Asset found = em.find(Asset.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }

    public Asset assetfromTag(Tag tg) {

        return em.createQuery("select e from Asset e where e.canceled = false AND e.tag.id = :id", Asset.class)
                .setParameter("id", tg.getId())
                .getSingleResult();
    }

    public List<JSONObject> monitoring(Long id) {

        List<JSONObject> jlMonitoring = new ArrayList<>();

        String sql = "SELECT "
                + "a.id as 'asset_id', "
                + "a.name as 'asset_name', "
                + "DATE_FORMAT(a.activation, '%Y-%m-%dT%H:%i:%s') as 'asset_activation', "
                + "t.id as 'tag_id', "
                + "t.address as 'tag_macaddress', "
                + "DATE_FORMAT(t.activation, '%Y-%m-%dT%H:%i:%s') as 'tag_activation', "
                + "t.status as 'tag_status', "
                + "t.status_use as 'tag_use', "
                + "d.status as 'discovery_status', "
                + "d.status_use 'discovery_use', "
                + "d.msg_id, "
                + "DATE_FORMAT(m.recevedat, '%Y-%m-%dT%H:%i:%s') 'discovery_date', "
                + "c.id as 'controller_id', "
                + "c.name as 'controller', "
                + "c.address, "
                + "c.geolocation, "
                + "c.responsible, "
                + "p.id as 'company_id', "
                + "p.name as 'company_name' "
                + "FROM "
                + "asset a "
                + "inner join tag t on a.tag_id = t.id "
                + "inner join tagdiscoverylog d on t.id = d.tag_id "
                + "inner join controller c on a.controller_id = c.id "
                + "inner join company p on c.company_id = p.id "
                + "inner join messagein m on d.msg_id = m.id "
                + "WHERE "
                + "c.id = :id "
                + "ORDER BY "
                + "a.id, "
                + "m.recevedat DESC";

        List<Object[]> resultList = em.createNativeQuery(sql)
                .setParameter("id", id)
                .getResultList();

        for (Object[] row : resultList) {
                
            JSONObject jsonRow = new JSONObject();
            jsonRow.put("asset_id", row[0]);
            jsonRow.put("asset_name", row[1]);
            jsonRow.put("asset_activation", row[2]);
            jsonRow.put("tag_id", row[3]);
            jsonRow.put("tag_macaddress", row[4]);
            jsonRow.put("tag_activation", row[5]);
            jsonRow.put("tag_status", row[6]);
            jsonRow.put("tag_use", row[7]);
            jsonRow.put("discovery_status", row[8]);
            jsonRow.put("discovery_use", row[9]);
            jsonRow.put("msg_id", row[10]);
            jsonRow.put("discovery_date", row[11]);
            jsonRow.put("controller_id", row[12]);
            jsonRow.put("controller", row[13]);
            jsonRow.put("address", row[14]);
            jsonRow.put("geolocation", row[15]);
            jsonRow.put("responsible", row[16]);
            jsonRow.put("company_id", row[17]);
            jsonRow.put("company_name", row[18]);
            jlMonitoring.add(jsonRow);
        }

        return jlMonitoring;
    }

    @Override
    public Asset update(Asset obj) {

        AssetTagLog old = s_assetlog.findLog(obj);

        if (old.getId() != null) {
            if (!Objects.equals(old.getTag().getId(), obj.getTag().getId())) {
                old.setStatus(0);
                s_assetlog.update(old);

                AssetTagLog atl = new AssetTagLog();
                atl.setAsset(obj);
                atl.setTag(obj.getTag());
                atl.setStatus(1);
                s_assetlog.save(atl);
            }
        } else {
            
            AssetTagLog atl = new AssetTagLog();
            atl.setAsset(obj);
            atl.setTag(obj.getTag());
            atl.setStatus(1);

            s_assetlog.save(atl);
        }

        return super.update(obj);
    }

    @Override
    public Asset save(Asset obj) {

        Asset saved = super.save(obj);

        if (saved.getTag() != null) {

            AssetTagLog atl = new AssetTagLog();
            atl.setAsset(saved);
            atl.setTag(saved.getTag());
            atl.setStatus(1);

            s_assetlog.save(atl);
        }

        return saved;
    }

}
