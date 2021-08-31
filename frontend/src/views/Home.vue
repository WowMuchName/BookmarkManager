<template lang="pug">
  div
    b-row
      b-jumbotron(
        header="Bookmark Manager"
        lead="Example crud application with a spring & vue stack"
        bg-variant="info"
        text-variant="white")
    b-row: b-col
      span Table goes here
    b-row: b-col
      b-button(variant="primary" @click="createBookmarkDialogVisible=true" ) Create
    //- Dialogs
    b-modal(v-model="createBookmarkDialogVisible" ok-title="Save" @ok="createBookmark(creatingBookmark)")
      bookmark-editor(v-model="creatingBookmark")
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import {
  BookmarkCreationV1,
  BookmarkV1
} from "@/remote-client";
import { remoteApi } from "@/lib/Api";

@Component({
})
export default class Home extends Vue {
  private createBookmarkDialogVisible = false;
  private creatingBookmark: BookmarkCreationV1 = {
    name: "My amazing bookmark",
    url: "https://google.com",
    description: "Description for the bookmark",
  };

  public async createBookmark(bookmark: BookmarkCreationV1) {
    let result = await remoteApi.bookmark.create(bookmark);
  }

  public async refreshTable() {

  }
}
</script>
