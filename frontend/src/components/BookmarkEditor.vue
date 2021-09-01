<template lang="pug">
  b-form(@submit="onSubmit")
    b-form-group(label="Name" :state="nameValid" invalid-feedback="Must be between 3 & 32 characters long")
      b-form-input(v-model="value.name" :state="nameValid")
    b-form-group(label="URL" :state="urlValid" invalid-feedback="Must be a valid url")
      b-form-input(v-model="value.url" :state="urlValid")
    b-form-group(label="Description" :state="descriptionValid" invalid-feedback="Must not exceed 128 characters")
      b-form-input(v-model="value.description" :state="descriptionValid")
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
import { Validator } from "@/lib/Validator";

@Component
export default class BookmarkEditor extends Vue {
  @Prop({
    required: true,
  })
  public value!: BookmarkV1 | BookmarkCreationV1;

  private get nameValid() {
    return Validator.bookmarkNameValid(this.value);
  }

  private get urlValid() {
    return Validator.bookmarkUrlValid(this.value);
  }

  private get descriptionValid() {
    return Validator.bookmarkDescriptionValid(this.value);
  }

  private onSubmit() {
    this.$emit("submit", this.value);
  }
}
</script>
