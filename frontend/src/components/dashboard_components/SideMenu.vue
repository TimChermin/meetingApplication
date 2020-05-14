<template>
    <div>
        <div class="has-padding-left mylogo" @click="$router.push('/')">
            <img class="is-marginless" src="../../assets/IsaacLogo.svg" alt="ISAAC" id="isaacLogo" />
        </div>

        <SideMenuList/>
    </div>
</template>

<script>
    import SideMenuList from "./SideMenuList";
    export default {
        name: "SideMenu",
        components:{
            SideMenuList
        },
        data() {
            return {
            };
        },
        methods: {

        }
    }
</script>

<style lang="scss" scoped>
    @import "src/assets/sass/styles.scss";

    #isaacLogo{
        height: 40px;
    }

    .mylogo{
        padding-top: 5px;
        height: 50px;
        background-color: #0084C3;
    }

    .has-padding-left{
        padding-left: 10px;
    }

    .has-padding-left-right-and-top{
        padding: 10px 10px 0 10px;
    }

    .has-border{
        border-bottom: $border-color;
    }

    .menu-label{
        font-size: 15px !important;
        color: #FFF;
    }

    .menu-list a{
        color: #FFF !important;
    }
</style>