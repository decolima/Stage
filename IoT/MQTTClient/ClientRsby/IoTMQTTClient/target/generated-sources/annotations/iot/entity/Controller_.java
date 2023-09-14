package iot.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-09-14T14:22:07", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Controller.class)
public class Controller_ extends BaseEntity_ {

    public static volatile SingularAttribute<Controller, Integer> discovery;
    public static volatile SingularAttribute<Controller, String> name;
    public static volatile SingularAttribute<Controller, Long> main_id;
    public static volatile SingularAttribute<Controller, String> company;
    public static volatile SingularAttribute<Controller, String> activation;
    public static volatile SingularAttribute<Controller, Integer> status;

}