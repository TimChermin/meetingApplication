<template>
    <div>
        <AdminLoginModal v-on:toggleLoginModal="toggleModal" v-bind:isModalActive="this.loginModalIsActive"/>
        <div class="columns">
            <div class="column is-one-fifth sidebar">
                <SideMenu/>
            </div>
            <div class="column is-four-fifths is-paddingless">
                <div class="navbar is-light mynavbar">
                    <NavBar/>
                </div>
                <div class="router-view">
                    <router-view class="has-padding-top-and-left"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import NavBar from "./dashboard_components/NavBar";
    import SideMenu from "./dashboard_components/SideMenu";
    import AdminLoginModal from "./dashboard_components/AdminLoginModal";
    export default {
        name: "AdminPage",
        components: {
            AdminLoginModal,
            NavBar,
            SideMenu
        },
        props:{
            loginModalIsActive: Boolean
        },
        methods:{
            toggleModal(){
                this.loginModalIsActive = !this.loginModalIsActive;
            }
        },
        beforeMount() {
            let token = localStorage.getItem("Authorization");
            this.loginModalIsActive = !(token && token.startsWith("Bearer="));
        }
    }
</script>

<style lang="scss" scoped>
    @import "src/assets/sass/styles.scss";

    .mynavbar{
        min-height: 50px !important;
        max-height: 50px !important;
        display: flex;
        align-items: center;
        padding-left: 16px;
    }

    .sidebar{
        background-color: rgb(0, 157, 220);
        background-image: url(../assets/IsaacBg.jpg);
        background-position: 90% 100%;
        background-size: 550%;
        background-repeat: no-repeat;
        padding: 0 0 0 0;
    }

    .router-view{
        height: calc(100vh - 50px);
    }

    .has-padding-top-and-left{
        padding: 16px 0 0 16px;
    }
</style>