package project.smartcity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Table(name = "driver")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Driver {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("username")
    @Column(name="username")
    private String username;

    @JsonProperty("pass")
    @Column(name="pass")
    private String pass;

    @JsonProperty("email")
    @Column(name="email")
    private String email;

    public Driver(){}

    public Driver(String username, String pass, String email){
        this.username = username;
        this.pass = pass;
        this.email = email;
    }
}
