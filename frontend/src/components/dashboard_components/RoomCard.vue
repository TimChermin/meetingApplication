<template>
    <div class="card">
        <p class="is-size-4 has-text-weight-semibold has-padding-left-right">{{room.name}}</p>

        <hr class="hider-line" />

        <div class="has-padding-left-right">
            <b-icon icon="stairs"/>
            <p class="is-size-5 inline-block">{{$t("assignRoom.location")}}: {{room.location}}</p>
        </div>

        <div class="buttonContainer has-padding-left-right">
            <b-button class="assignButton is-large" @click="assignRoomClicked" >{{$t("assignRoom.cardButton")}}</b-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "RoomCard",
        props:{
            room: Object
        },
        methods:{
            assignRoomClicked(){
                this.$apiService.assignRoom(this.room, localStorage.getItem("Authorization")).then(response => {
                    console.log(response);
                    if(response.status === 200) {
                        this.$helper.popupMessage("Successfully assigned room", "is-success");
                        localStorage.setItem("Room-Authorization", response.data);
                        this.$router.push("/");
                    }
                    else if(response.status === 400) this.$helper.popupMessage("Bad request!", "is-danger");
                    else if (response.status === 401) this.$helper.popupMessage("Bad request!", "is-danger");
                    else this.$helper.popupMessage("An unknown error has occurred, please try again!", "is-danger");
                })
            }
        }
    }
</script>

<style scoped>
    .card {
        border-radius: 4px;
        min-width: 16vw !important;
    }

    .inline-block{
        display: inline-block;
    }

    .hider-line {
        height: 3px;
        background-color: #2fa1ed;
        margin: 5px 0 5px 0 !important;
    }

    .assignButton {
        align-self: flex-end;
        position: relative;
        background-color: #2fa1ed !important;
        color: white !important;
        border: darkgray 1px !important;
    }

    .has-padding-left-right{
        padding: 0 10px 0 10px;
    }

    .buttonContainer{
        padding: 10px;
        align-items: center;
        justify-content: center;
        display: flex;
    }

    .reserveButton:hover {
        color: ghostwhite !important;
        border-color: white !important;
    }

    .reserveButton:focus {
        border-color: cornflowerblue !important;
        color: #363636 !important;
    }
</style>