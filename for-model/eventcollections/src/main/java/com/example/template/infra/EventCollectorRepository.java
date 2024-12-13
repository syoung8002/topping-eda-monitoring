path: /src/main/java/{{options.package}}/infra
---
package {{options.package}}.infra;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import {{options.package}}.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "eventCollectors",
    path = "eventCollectors"
)
public interface EventCollectorRepository
    extends PagingAndSortingRepository<EventCollector, Long> {

        List<EventCollector> findByCorrelationKey(String correlationKey);

        @Query("SELECT e FROM EventCollector e WHERE e.timestamp >= :timestamp")
        List<EventCollector> findRecentEvents(@Param("timestamp") Long timestamp);
    }
