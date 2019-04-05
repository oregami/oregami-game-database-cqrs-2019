package org.oregami.references.model;

import org.oregami.references.readmodel.RReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceRepository extends JpaRepository<RReference, String> {


    List<RReference> findByEventIdList(String eventId);
}
