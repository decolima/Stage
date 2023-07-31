/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */
import iot.entity.maps.TagLog;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class TagLogService {

    @PersistenceContext
    private EntityManager em;

    public List<TagLog> getAll() {

        this.em = DbManager.getInstance().getEM();

        List<Object[]> result = em.createNativeQuery("SELECT e.address, e.name, e.datadiscovery, e.status, e.idpublish FROM taglog e")
                .getResultList();

        List<Map<String, Object>> jsonList = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> jsonRow = new HashMap<>();
            jsonRow.put("address", row[0]);
            jsonRow.put("name", row[1]);
            jsonRow.put("datadiscovery", row[2]);
            jsonRow.put("status", row[3]);
            jsonRow.put("idpublish", row[4]);
            jsonList.add(jsonRow);
        }

        List<TagLog> listLog = new ArrayList<>();

        for (Map<String, Object> e : jsonList) {
            // Extract the values from the JSON object and create a new TagLog object.

            String address = e.get("address") != null ? e.get("address").toString() : "";
            String name = e.get("name") != null ? e.get("name").toString() : "";
            String datadiscovery = e.get("datadiscovery") != null ? e.get("datadiscovery").toString() : "";
            String status = e.get("status") != null ? e.get("status").toString() : "";
            String idpublish = e.get("idpublish") != null ? e.get("idpublish").toString() : "";

            TagLog tagLog = new TagLog(address, name, datadiscovery, status, idpublish);

            listLog.add(tagLog);
        }

        return listLog;

    }

    public List<TagLog> getNews() {

        this.em = DbManager.getInstance().getEM();

        List<Object[]> result = em.createNativeQuery("SELECT e.address, e.name, e.datadiscovery, e.status, e.idpublish FROM taglog e WHERE e.idpublish = 0 ORDER BY e.datadiscovery DESC")
                .getResultList();

        List<Map<String, Object>> jsonList = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> jsonRow = new HashMap<>();
            jsonRow.put("address", row[0]);
            jsonRow.put("name", row[1]);
            jsonRow.put("datadiscovery", row[2]);
            jsonRow.put("status", row[3]);
            jsonRow.put("idpublish", row[4]);
            jsonList.add(jsonRow);
        }

        List<TagLog> listLog = new ArrayList<>();

        for (Map<String, Object> e : jsonList) {
            // Extrair os valores do objeto JSON e criar um novo objeto TagLog

            String address = e.get("address") != null ? e.get("address").toString() : "";
            String name = e.get("name") != null ? e.get("name").toString() : "";
            String datadiscovery = e.get("datadiscovery") != null ? e.get("datadiscovery").toString() : "";
            String status = e.get("status") != null ? e.get("status").toString() : "";
            String idpublish = e.get("idpublish") != null ? e.get("idpublish").toString() : "";

            TagLog tagLog = new TagLog(address, name, datadiscovery, status, idpublish);
            // Adicionar o objeto TagLog à lista listLog
            listLog.add(tagLog);
        }

        return listLog;

    }

    public List<TagLog> getLogPubliehd(Long id) {

        this.em = DbManager.getInstance().getEM();

        List<Object[]> result = em.createNativeQuery("SELECT e.address, e.name, e.datadiscovery, e.status, e.idpublish FROM taglog e WHERE e.idpublish = :id ORDER BY e.datadiscovery DESC")
                .setParameter("id", id)
                .getResultList();

        List<Map<String, Object>> jsonList = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> jsonRow = new HashMap<>();
            jsonRow.put("address", row[0]);
            jsonRow.put("name", row[1]);
            jsonRow.put("datadiscovery", row[2]);
            jsonRow.put("status", row[3]);
            jsonRow.put("idpublish", row[4]);
            jsonList.add(jsonRow);
        }

        List<TagLog> listLog = new ArrayList<>();

        for (Map<String, Object> e : jsonList) {
            // Extrair os valores do objeto JSON e criar um novo objeto TagLog

            String address = e.get("address") != null ? e.get("address").toString() : "";
            String name = e.get("name") != null ? e.get("name").toString() : "";
            String datadiscovery = e.get("datadiscovery") != null ? e.get("datadiscovery").toString() : "";
            String status = e.get("status") != null ? e.get("status").toString() : "";
            String idpublish = e.get("idpublish") != null ? e.get("idpublish").toString() : "";

            TagLog tagLog = new TagLog(address, name, datadiscovery, status, idpublish);
            // Adicionar o objeto TagLog à lista listLog
            listLog.add(tagLog);
        }

        return listLog;

    }

    public boolean updatePublish(String newIdpublish, List<TagLog> tagLog) {

        try {

            List<String> address = tagLog.stream()
                    .filter(e -> e.getStatus_tag() != 9)
                    .map(e -> e.getAddress())
                    .collect(Collectors.toList());

            if (!address.isEmpty()) {

                String updateSql = "UPDATE taglog SET idpublish = " + newIdpublish
                        + " WHERE idpublish = 0 and address IN (";

                for (String t : address) {
                    updateSql += "'" + t + "', ";
                }

                updateSql = updateSql.substring(0, updateSql.length() - 2);
                updateSql += ")";

                System.out.println(updateSql);

                this.em = DbManager.getInstance().getEM();

                // Abrir uma transação para fazer o update
                em.getTransaction().begin();
                em.createNativeQuery(updateSql)
                        //.setParameter("newIdpublish", newIdpublish)
                        //.setParameter("address", ad)
                        .executeUpdate();

                em.getTransaction().commit();

                return true;
            }
            else
            {
                return true;
            }
        
        }catch (Exception e) {
            return false;
        }
    }
}
