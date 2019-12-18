package org.cloudcafe.boo2.ms.social.app.repository;

import org.cloudcafe.boo2.ms.social.app.model.Chapter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChapterRepo extends ReactiveCrudRepository<Chapter, String> {
}
