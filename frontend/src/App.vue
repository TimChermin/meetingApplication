<template>
    <router-view/>
</template>

<script>
export default {
  name: "App"
};
</script>

<style lang="scss">
    @import "~bulma/sass/utilities/_all";
    @import "~bulma";
    @import "~buefy/src/scss/buefy";

    $menu-item-color: hsl(0, 0%, 96%);
    $menu-item-hover-background-color: hsl(217, 71%, 53%);
</style>
