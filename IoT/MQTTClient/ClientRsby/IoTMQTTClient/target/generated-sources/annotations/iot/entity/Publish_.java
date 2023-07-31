package iot.entity;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-28T18:28:57", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Publish.class)
public class Publish_ extends BaseEntity_ {

    public static volatile SingularAttribute<Publish, LocalDateTime> publishdate;
    public static volatile SingularAttribute<Publish, Integer> status;

}