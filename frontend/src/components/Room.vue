<template>
  <div class="card">
    <div class="card-header">
      <p class="card-header-title">{{room.name}}</p>
    </div>
    <hr class="hider-line" />
    <div class="card-content">
      <div class="content">
        <div class="bigboi-text">
          <b-icon id="RoomLocation" class="left-icon" icon="stairs" size="is-small"></b-icon>
          {{room.location}}
        </div>
        <br/>
        <div class="bigboi-text">
          <b-icon class="left-icon" icon="account-multiple" size="is-small"></b-icon>
          <div id="RoomPeopleTemplate" class="margin-left">{{room.maxPersons}} ... {{$t("freeMeetingRooms.people")}}</div>
        </div>
        <br/>
        <div id="RoomFreeDiv" class="bigboi-text" v-if="room.status === 'FREE'">
          <b-icon class="left-icon" icon="clock-outline" size="is-small"></b-icon>
          {{$t("freeMeetingRooms.until")}} {{this.createTime(new Date(room.startTime * 1000))}}
        </div>
        <div id="RoomUnbookedDiv" class="bigboi-text" v-else-if="room.status === 'UNBOOKED'">
          <b-icon class="left-icon" icon="clock-outline" size="is-small"></b-icon>
          {{$t("unbooked.explain")}}
        </div>
        <br />
      </div>
    </div>
    <b-button id="RoomBtnReserve" class="reserveButton is-large" @click="reserveButtonClick">{{$t("freeMeetingRooms.reserve")}}</b-button>

  </div>
</template>
<script>
export default {
  name: "Room",
  props: {room: Object},
  methods: {
    createTime(date) {
      return (
        (date.getHours() < 10 ? "0" : "") +
        date.getHours() +
        ":" +
        (date.getMinutes() < 10 ? "0" : "") +
        date.getMinutes()
      );
    },
    reserveButtonClick() {
      this.$emit("toggleCreateMeetingModal");
    }
  }
};
</script>

<style scoped>

  #RoomPeopleTemplate{
    display: inline-block;
  }

.card {
  border-radius: 4px;
}
.card-content {
  left: 0 !important;
  padding-left: 0;
  margin-left: 5%;
  width: 300px;
  height: 200px;
}
.margin-left {
  margin-left: 2% !important;
}
.bigboi-text {
  font-size: 1.5rem;
}

.hider-line {
  margin: 0 0 -12px;
  height: 3px;
  background-color: #2fa1ed;
}
.reserveButton {
  align-self: flex-end;
  position: relative;
  background-color: #2fa1ed !important;
  color: white !important;
  border: darkgray 1px !important;
  margin-left: 4%;
  margin-bottom: 4%;
}

.reserveButton:hover {
  color: ghostwhite !important;
  border-color: white !important;
}

.reserveButton:focus {
  border-color: cornflowerblue !important;
  color: #363636 !important;
}

.card-header-title {
  font-size: 1.5em;
  font-weight: 600;
}
</style>