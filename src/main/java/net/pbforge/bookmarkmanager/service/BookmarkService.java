package net.pbforge.bookmarkmanager.service;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1;
import net.pbforge.bookmarkmanager.entity.BookmarkV1;
import net.pbforge.bookmarkmanager.dao.BookmarkDao;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkService {
    BookmarkDao bookmarkDao;

    public Flux<BookmarkV1> getBookmarks() {
        return bookmarkDao.findAll();
    }

    public Mono<BookmarkV1> getBookmark(@NonNull String id) {
        return bookmarkDao.findById(id);
    }

    public Mono<Void> deleteBookmark(@NonNull String id) {
        return bookmarkDao.deleteById(id);
    }

    public Mono<BookmarkV1> createBookmark(@NonNull BookmarkCreationV1 bookmarkCreation) {
        return bookmarkDao.save(bookmarkCreation.toBookmark());
    }

    public Mono<BookmarkV1> updateBookmark(@NonNull BookmarkV1 bookmark) {
        // Note: We cannot make id an required field on entity level without making our live much harder.
        // In the context of the update however it is required, which is why we need this special check on service
        // level.
        Preconditions.checkArgument(bookmark.getId() != null, "No Id specified for bookmark");
        return bookmarkDao.existsById(bookmark.getId()).flatMap(exists -> {
            // Note: When we upsert an element that does not exist it is created. We want this method to be a pure
            // update as there might be cases where users are allowed to update stuff but not create. Hence we do
            // this additional check here.
            if (!Boolean.TRUE.equals(exists)) {
                return Mono.error(new NoSuchElementException());
            }
            return bookmarkDao.save(bookmark);
        });
    }
}
