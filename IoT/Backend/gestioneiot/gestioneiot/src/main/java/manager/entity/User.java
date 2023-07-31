
package manager.entity;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import manager.boundary.UsersResources;
import manager.entity.constant.BaseEntity;
import manager.entity.constant.UserRoles;



/**
 *
 * @author tss
 */
@Entity
@Table(name = "user")
public class User extends  BaseEntity {
    
    @NotBlank
    @Column(nullable = false)
    private String firstname;
    
    @NotBlank
    @Column(nullable = false)    
    private String lastname;
    
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)    
    private String email;
    
    @NotBlank
    @Size(min = 4)
    @Column(nullable = false)    
    private String pwd;
        
    @Enumerated(EnumType.STRING)
    UserRoles roleuser;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonbTransient
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserRoles getRoleuser() {
        return roleuser;
    }

    public void setRoleuser(UserRoles roleuser) {
        this.roleuser = roleuser;
    }

    
   
   
    public JsonObject toJsonSliceName() {

        return Json.createObjectBuilder()
                .add("firstname", this.firstname)
                .add("link", UriBuilder.fromResource(UsersResources.class)
                        .path(UsersResources.class,"find")
                        .build(this.id).toString())
                .build();
    }
  
}
