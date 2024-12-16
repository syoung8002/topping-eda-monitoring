path: /src/main/java/{{options.package}}/infra
---
package {{options.package}}.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.template.config.kafka.KafkaProcessor;
import com.example.template.domain.EventCollector;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventCollectorViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private EventCollectorRepository eventCollectorRepository;

{{#boundedContexts}}
    {{#each attached}}
    {{#if (isEvent _type)}}
    @StreamListener(KafkaProcessor.INPUT)
    public void when{{namePascalCase}}_then_CREATE_1(
        @Payload {{namePascalCase}} {{nameCamelCase}}
    ) {
        try {
            if (!{{nameCamelCase}}.validate()) return;

            // view 객체 생성
            EventCollector eventCollector = new EventCollector();
            // view 객체에 이벤트의 Value 를 set 함
            eventCollector.setType({{nameCamelCase}}.getEventType());
            eventCollector.setCorrelationKey(
                String.valueOf({{nameCamelCase}}.getId())
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString({{nameCamelCase}});
            eventCollector.setPayload(jsonPayload);
            eventCollector.setTimestamp({{nameCamelCase}}.getTimestamp());
            // view 레파지 토리에 save
            eventCollectorRepository.save(eventCollector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {{/if}}
    {{/each}}
{{/boundedContexts}}
    //>>> DDD / CQRS
}
<function>
window.$HandleBars.registerHelper('isEvent', function (type) {
    if (type.endsWith("Event")) {
        return true;
    } else {
        return false;
    }
});
</function>