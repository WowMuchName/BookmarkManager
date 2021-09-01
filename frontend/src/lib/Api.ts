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

  private extractDetailFromProblemJson(exception: any) {
    // If the response is application/problem+json and has a cause field
    // extract it an present it as the error. Otherwise use the original (which will
    // stringify to something like "Error: Status-Code 400"
    return exception?.response?.data?.detail || exception;
  }

  public async deleteBookmark(id: string) {
    try {
      await remoteApi.bookmark._delete(id);
    } catch (e) {
      throw this.extractDetailFromProblemJson(e);
    }
  }

  public async updateBookmark(bookmark: BookmarkV1): Promise<BookmarkV1> {
    try {
      let result = await remoteApi.bookmark.update(bookmark, {
        headers: {
          "Content-Type": "application/vnd.net.pbforge.bookmark-v1+json",
        },
      });
      return result.data;
    } catch (e) {
      throw this.extractDetailFromProblemJson(e);
    }
  }

  public async listBookmarks(): Promise<BookmarkV1[]> {
    try {
      let result = await remoteApi.bookmark.list();
      return result.data;
    } catch (e) {
      throw this.extractDetailFromProblemJson(e);
    }
  }

  public async createBookmark(bookmarkCreation: BookmarkCreationV1): Promise<BookmarkV1> {
    try {
      let result = await remoteApi.bookmark.create(bookmarkCreation, {
        headers: {
          "Content-Type": "application/vnd.net.pbforge.bookmark-creation-v1+json",
        }
      });
      return result.data;
    } catch (e) {
      throw this.extractDetailFromProblemJson(e);
    }
  }
}

export const remoteApi = new RemoteApi()
