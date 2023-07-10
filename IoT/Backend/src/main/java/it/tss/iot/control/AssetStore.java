/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.iot.control;

import it.tss.iot.Control;
import it.tss.iot.entity.Azienda;
import it.tss.iot.entity.Asset;
import java.util.List;

/**
 *
 * @author ospite
 */
@Control
public class SalaStore extends AbstractStore<Asset> {

    public SalaStore() {
        super(Asset.class);
    }

    public List<Asset> all() {
        return em.createNamedQuery(Asset.FIND_ALL, Asset.class)
                .getResultList();
    }
}