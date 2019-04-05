package org.oregami.references.event;

public class UrlAddedEvent {

    private final String id;
    private String url;

    public UrlAddedEvent(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
