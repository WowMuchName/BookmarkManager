import { BookmarkResourceApi } from "@/remote-client";

export class RemoteApi {
  public bookmark: BookmarkResourceApi = new BookmarkResourceApi();
}

export const remoteApi = new RemoteApi()
