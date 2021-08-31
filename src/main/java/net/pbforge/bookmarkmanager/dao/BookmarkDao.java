package net.pbforge.bookmarkmanager.dao;

import net.pbforge.bookmarkmanager.entity.BookmarkV1;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookmarkDao extends ReactiveCrudRepository<BookmarkV1, String> {
}
