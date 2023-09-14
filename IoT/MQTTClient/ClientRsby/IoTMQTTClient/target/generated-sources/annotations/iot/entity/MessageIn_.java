package iot.entity;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-09-14T14:22:07", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(MessageIn.class)
public class MessageIn_ extends BaseEntity_ {

    public static volatile SingularAttribute<MessageIn, LocalDateTime> recevedat;
    public static volatile SingularAttribute<MessageIn, String> message;
    public static volatile SingularAttribute<MessageIn, Integer> status;

}