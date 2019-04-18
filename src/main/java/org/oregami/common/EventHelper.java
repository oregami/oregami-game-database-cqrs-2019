package org.oregami.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventHelper {

    @Autowired
    private EventStore eventStore;

    public Map<String, Object> getEventsWithIds(String aggregateId, List<String> eventIds) {
        Map<String, Map<String, Object>> allEvents = getEventInformation(aggregateId);

        Map<String, Object> result = new TreeMap<>();

        for (Map<String, Object> value: allEvents.values()) {
            if (eventIds.contains(value.get("Identifier"))) {
                result.put(value.get("Timestamp").toString(), value);
            }
        }

        return result;
    }

    public Map<String, Object> getEventsWithIds(List<String> aggregateIds, List<String> eventIds) {
        Map<String, Map<String, Object>> allEvents = getEventInformation(aggregateIds);

        Map<String, Object> result = new TreeMap<>();

        for (Map<String, Object> value: allEvents.values()) {
            if (eventIds.contains(value.get("Identifier"))) {
                result.put(value.get("Timestamp").toString(), value);
            }
        }

        return result;
    }

    public Map<String, Map<String, Object>> getEventInformation(List<String> aggregateIdList) {
        Map<String, Map<String, Object>> result = new TreeMap<>();
        for (String id: aggregateIdList) {
            Map<String, Map<String, Object>> map = getEventInformation(id);
            result.putAll(map);
        }
        return result;
    }

    public Map<String, Map<String, Object>> getEventInformation(String aggregateId) {
        Map<String, Map<String, Object>> result = new TreeMap<>();

        ObjectMapper mapper = new ObjectMapper();

        DomainEventStream domainEventStream = eventStore.readEvents(aggregateId);
        Iterator<? extends DomainEventMessage<?>> iterator = domainEventStream.asStream().iterator();
        while (iterator.hasNext()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            DomainEventMessage<?> event = iterator.next();
            map.put("Identifier", event.getIdentifier());
            map.put("SequenceNumber", event.getSequenceNumber());

            Date myDate = Date.from(event.getTimestamp());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
            String formattedDate = formatter.format(myDate);

            map.put("Timestamp", formattedDate);
            map.put("TimestampOriginalFormat", event.getTimestamp());
            map.put("PayloadTypeSimple", event.getPayloadType().getSimpleName());
            map.put("PayloadType", event.getPayloadType().getName());
            map.put("Payload", event.getPayload());
            try {
                map.put("PayloadToString", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event.getPayload()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            map.put("MetaData", event.getMetaData());
            map.put("MetaDataToString", ToStringBuilder.reflectionToString(event.getMetaData(), RecursiveToStringStyle.JSON_STYLE));

            try {
                map.put("username", ((KeycloakPrincipal)(event.getMetaData().get("userId"))).getKeycloakSecurityContext().getToken().getPreferredUsername());
            } catch (ClassCastException e) {
                map.put("username", event.getMetaData().get("userId"));
            }



            result.put(formattedDate, map);
            //result.put(event.getTimestamp(), map);
        }

        return result;

    }

    public boolean check(Map<String, Map<String, Object>> events, String eventId) {
        for(Map<String, Object> e: events.values()) {
            if (e.get("Identifier").equals(eventId)) return true;
        }
        return false;
    }
}
