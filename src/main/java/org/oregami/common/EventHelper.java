package org.oregami.common;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class EventHelper {

    @Autowired
    private EventStore eventStore;

    public Map<Instant, Map<String, Object>> getEventInformation(String aggregateId) {
        Map<Instant, Map<String, Object>> result = new TreeMap<>();

        DomainEventStream domainEventStream = eventStore.readEvents(aggregateId);
        Iterator<? extends DomainEventMessage<?>> iterator = domainEventStream.asStream().iterator();
        while (iterator.hasNext()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            DomainEventMessage<?> event = iterator.next();
            map.put("Identifier", event.getIdentifier());
            map.put("SequenceNumber", event.getSequenceNumber());

            map.put("Timestamp", event.getTimestamp());
            map.put("Payload", event.getPayloadType().getSimpleName() + ": " + ToStringBuilder.reflectionToString(event.getPayload(), RecursiveToStringStyle.JSON_STYLE));
            map.put("MetaData", ToStringBuilder.reflectionToString(event.getMetaData(), RecursiveToStringStyle.JSON_STYLE));
            result.put(event.getTimestamp(), map);
        }

        return result;

    }
}
