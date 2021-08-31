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
      b-button(variant="primary") Create
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src

@Component({
})
export default class Home extends Vue {}
</script>
