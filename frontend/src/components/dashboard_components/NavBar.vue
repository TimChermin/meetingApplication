<template>
    <div class="spread" >
        <input class="searchInput" placeholder="Search..."/>
        <b-button @click="$router.push('/')" icon-left="home" class="home-button">Home</b-button>
    </div>
</template>

<script>
    export default {
        name: 'NavBar',
        components: {
        }
    }
</script>

<style scoped>
    .searchInput{
        border: none transparent;
        background-color: transparent;
        height: 35px;
        font-size: 20px;
        width: 15%;
        border-bottom: 1px solid gray;
        padding-left: 8px;
    }

    .spread {
        width: 100%;
        display: flex !important;
        justify-content: space-between !important;
        align-items: center !important;
    }

    .home-button{
        background-color: transparent;
        border-color: transparent;
        text-decoration: underline;
    }
</style>