path: /src/main/java/{{options.package}}/domain
---
package {{options.package}}.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "EventCollector_table")
@Data
public class EventCollector {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String type;
    private String correlationKey;
    
    @Lob
    private String payload;
    private Long timestamp;
}
