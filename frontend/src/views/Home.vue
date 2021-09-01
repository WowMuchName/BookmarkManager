<style lang="sass">
.bookmark-table__actions button
  margin-right: 5px
#home__alert
  margin-top: 10px
.home__actions button
  margin-left: 5px
</style>

<template lang="pug">
  div(id="home")
    b-row
      b-jumbotron(
        header="Bookmark Manager"
        lead="Example crud application with a spring & vue stack"
        bg-variant="info"
        text-variant="white")
    //- Error-Message
    b-row: b-col: b-alert(variant="danger" :show="lastError!=null" id="home__alert") {{ lastError }}

    //- CRUD Table
    b-row: b-col
      b-table(striped hover :items="visiblePage" :fields="bookmarkColumns" :busy="busy" class="bookmark-table")
        //- Busy indicator
        template(#table-busy)
          div(class="text-center")
            b-spinner
        //- Per row actions
        template(#cell(actions)="row")
          div(class="bookmark-table__actions")
            b-button(size="sm" @click="editBookmark(row.item)" ): b-icon-pencil-square
            b-button(variant="danger" size="sm" @click="deleteBookmark(row.item)" ): b-icon-trash

    //- Toolbar
    b-row: b-col(class="home__actions text-center")
      b-button(variant="primary" @click="createBookmarkDialogVisible=true"): b-icon-plus
      b-button(variant="secondary" @click="refreshTable()" ): b-icon-arrow-clockwise

    //- Dialogs
    b-modal(v-model="createBookmarkDialogVisible" ok-title="Save" @ok="createBookmark(creatingBookmark)")
      bookmark-editor(v-model="creatingBookmark")
    b-modal(v-model="editBookmarkDialogVisible" ok-title="Update" @ok="updateBookmark(editingBookmark)")
      bookmark-editor(v-model="editingBookmark")
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

  private editBookmarkDialogVisible = false;
  private editingBookmark: BookmarkV1 = {
    id: "",
    name: "",
    url: "",
    description: "",
  };

  private busy = true;
  private visiblePage: BookmarkV1[] = [this.creatingBookmark as any];
  private bookmarkColumns = ["name", "url", "description", "actions"];
  private lastError: string | null = null;

  /**
   * Perform the supplied action. Display a busy indicator for the duration.
   * If an error occurs display the error-message and throw the exception upwards.
   * @param action to perform
   */
  private async runActionWithFeedback<T>(action: () => Promise<T>): Promise<T> {
    try {
      this.busy = true;
      let result = await action()
      this.lastError = null;
      return result;
    } catch (e) {
      this.lastError = e.toString();
      throw e;
    } finally {
      this.busy = false;
    }
  }

  public async createBookmark(bookmark: BookmarkCreationV1) {
    await this.runActionWithFeedback(async () => {
      await remoteApi.createBookmark(bookmark);
      await this.refreshTable();
    })
  }

  public editBookmark(bookmark: BookmarkV1) {
    // Note: Work on copy as we only want changes to take effect if form is submitted
    this.editingBookmark = JSON.parse(JSON.stringify(bookmark));
    this.editBookmarkDialogVisible = true;
  }

  public async deleteBookmark(bookmark: BookmarkV1) {
    await this.runActionWithFeedback(async () => {
      await remoteApi.deleteBookmark(bookmark.id);
      await this.refreshTable();
    });
  }

  public async updateBookmark(bookmark: BookmarkV1) {
    await this.runActionWithFeedback(async () => {
      await remoteApi.updateBookmark(bookmark);
      await this.refreshTable();
    });
  }

  public async refreshTable() {
    await this.runActionWithFeedback(async () => {
      this.visiblePage = await remoteApi.listBookmarks();
    });
  }

  // Note: Perform initial refresh when view is mounted
  public mounted() {
    this.refreshTable();
  }
}
</script>
