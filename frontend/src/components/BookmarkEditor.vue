<template lang="pug">
  b-form(@submit="onSubmit")
    b-form-group(label="Name")
      b-form-input(v-model="value.name")
    b-form-group(label="URL")
      b-form-input(v-model="value.url")
    b-form-group(label="Description")
      b-form-input(v-model="value.description")
</template>

<script lang="ts">
import {
  Vue,
  Prop,
  Component
} from "vue-property-decorator";
import {
  BookmarkCreationV1,
  BookmarkV1
} from "@/remote-client";

@Component
export default class BookmarkEditor extends Vue {
  @Prop({
    required: true,
  })
  public value!: BookmarkV1 | BookmarkCreationV1;

  private onSubmit() {
    this.$emit("submit", this.value);
  }
}
</script>
