import {
  BookmarkCreationV1,
  BookmarkV1
} from "@/remote-client";

export type BookmarkLike = BookmarkV1 | BookmarkCreationV1;

// Note:
// The support for validation in bootstrap-vue makes it hard to forward the validation result of a form
// to a parent component. Hence the validators have been collected here to prevent code duplication.
export class Validator {
  public static bookmarkNameValid(bookmark: BookmarkLike): boolean {
    return bookmark.name.length >= 3 && bookmark.name.length <= 32;
  }

  public static bookmarkUrlValid(bookmark: BookmarkLike): boolean {
    return bookmark.url.startsWith("http://") || bookmark.url.startsWith("https://")
  }

  public static bookmarkDescriptionValid(bookmark: BookmarkLike): boolean {
    return !bookmark.description || bookmark.description.length <= 128;
  }

  public static bookmarkValid(bookmark: BookmarkLike): boolean {
    return Validator.bookmarkNameValid(bookmark) && Validator.bookmarkDescriptionValid(bookmark)
     && Validator.bookmarkUrlValid(bookmark)
  }
}
