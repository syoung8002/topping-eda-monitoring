path: /src/main/java/{{options.package}}/infra
---
package {{options.package}}.infra;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.template.domain.EventCollector;

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
{{#boundedContexts}}
{{#each attached}}
    {{#if (isEvent _type name)}}
        {{#setSearchKey fieldDescriptors}}
        @Query("SELECT e FROM EventCollector e WHERE e.correlationKey = :correlationKey OR (:{{nameCamelCase}} IS NOT NULL AND e.{{nameCamelCase}} = :{{nameCamelCase}})")
        List<EventCollector> findBySearchKey(
            @Param("correlationKey") String correlationKey,
            @Param("{{nameCamelCase}}") String {{nameCamelCase}}
        );
        {{/setSearchKey}}
    {{/if}}
{{/each}}
{{/boundedContexts}}
    }

<function>
var eventList = [];
var searchKeyList = [];

window.$HandleBars.registerHelper('isEvent', function (type, name) {
    if (type.endsWith("Event") && !eventList.includes(name)) {
        eventList.push(name);
        return true;
    } else {
        return false;
    }
});

window.$HandleBars.registerHelper('setSearchKey', function (fieldDescriptors) {
    var result = null;
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].isSearchKey && !searchKeyList.includes(fieldDescriptors[i].name)) {
            searchKeyList.push(fieldDescriptors[i].name);
            result = fieldDescriptors[i];
        }
    }
    return result;
});
</function>