<template>
    <div id="app">
        <div class="columns">
            <Main v-on:roomAssigned="roomAssigned"/>
            <Sidebar :room="room" />
        </div>
    </div>
</template>

<script>
    import Main from "./Main";
    import Sidebar from "./Sidebar";
    export default {
        name: "Home",
        components: {
            Main,
            Sidebar
        },
        data(){
            return{
                room: {
                    name: "Unassigned tablet",
                    location: "Unassigned tablet",
                    email: "Unassigned tablet"
                }
            }
        },
        methods:{
            roomAssigned(assignedRoom){
                this.room = assignedRoom;
            }
        },
        beforeMount() {
            if(localStorage.getItem("Authorization")) localStorage.removeItem("Authorization");
        }
    }
</script>

<style scoped>

</style>