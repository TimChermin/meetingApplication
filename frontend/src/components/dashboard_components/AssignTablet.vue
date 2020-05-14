<template>
    <div class="rooms">
        <h1 class="title is-size-1 title-freeroom">{{$t("assignRoom.title")}}</h1>
        <div class="columns">
            <div v-bind:key="room.id" v-for="room in this.rooms">
                <div class="roomcard">
                    <RoomCard :room="room"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import RoomCard from "./RoomCard";
    export default {
        name: "AssignTablet",
        components:{
            RoomCard
        },
        data(){
            return{
                rooms: [],
                loadingComponent: {},
            }
        },
        beforeMount() {
            let self = this;
            self.loadingComponent = this.$buefy.loading.open();
            this.$apiService.getRooms().then((response) => {
                console.log(response);
                this.rooms = response.data;
            }).finally(function () {
                self.loadingComponent.close();
            });
        }
    }
</script>

<style scoped>
    .columns {
        width: 100% !important;
        flex-wrap: wrap;
        max-height: calc(100vh - 50px - 16px - 3rem);
        overflow-y: auto;
    }

    .rooms{
        max-height: calc(100vh - 50px);
    }
    .title{
        color: black !important;
    }

    .roomcard {
        display: inline-block;
        margin: 16px;
    }
</style>