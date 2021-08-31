<template lang="pug">
  b-container(id="app" tag="article")
    router-view
    b-row(tag="footer"): b-col
      span Made with ♥ by Mark Albert
</template>

<style lang="sass">
#app
  margin-top: 15px
  box-shadow: #00000044 1px 1px 3px 3px
  footer
    background: lightgray
    margin-top: 10px
    text-align: center
</style>
