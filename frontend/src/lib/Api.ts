import {
  BookmarkCreationV1,
  BookmarkResourceApi,
  BookmarkV1,
  Configuration
} from "@/remote-client";

export class RemoteApi {
  public configuration = new Configuration({
    // Note: Allow the base-url to be overwritten by a localstorage configuration for development.
    // Otherwise use the same URL the SPA is served from.
    basePath: localStorage.backend || location.protocol + "//" + location.host,
  });
  public bookmark: BookmarkResourceApi = new BookmarkResourceApi(this.configuration);

  public async deleteBookmark(id: string) {
    await remoteApi.bookmark._delete(id);
  }

  public async updateBookmark(bookmark: BookmarkV1): Promise<BookmarkV1> {
    let result = await remoteApi.bookmark.update(bookmark, {
      headers: {
        "Content-Type": "application/vnd.net.pbforge.bookmark-v1+json",
      }
    });
    return result.data;
  }

  public async listBookmarks(): Promise<BookmarkV1[]> {
    let result = await remoteApi.bookmark.list();
    return result.data;
  }

  public async createBookmark(bookmarkCreation: BookmarkCreationV1): Promise<BookmarkV1> {
    let result = await remoteApi.bookmark.create(bookmarkCreation, {
      headers: {
        "Content-Type": "application/vnd.net.pbforge.bookmark-creation-v1+json",
      }
    });
    return result.data;
  }
}

export const remoteApi = new RemoteApi()
