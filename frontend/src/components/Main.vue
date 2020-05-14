<template>
  <div
    class="column main is-three-quarters"
    v-bind:class="{ occupied: status === Status.OCCUPIED,  reserved: status === Status.RESERVED}"
  >
    <extend-meeting-modal
      v-on:toggleModal="toggleExtendModal"
      v-bind:extendMeetingModal="this.extendModal"
      v-bind:roomName="room.name"
    />
    <CreateMeeting v-bind:isActive="modalActive" v-bind:roomName="room.name" v-on:toggleOff="toggleCreateMeetingOff"/>
    <FreeRoom v-on:closeModal="toggleFreeModal" v-bind:isModalActive="this.freeModal" />

    <div class="spread">
      <div>
        <h1 class="title is-1">{{room.name}}</h1>
        <p>
          <b-icon icon="stairs" size="is-small"></b-icon>
          {{ $t("room.floor")}} {{room.location}}
          &emsp;
          <b-icon icon="account-multiple" size="is-small"></b-icon>
          {{persons}} {{ $t("room.size")}}
        </p>
      </div>
      <h1 class="subtitle is-1" id="time">{{ timestamp }}</h1>
    </div>
    <div class="center">
      <h1 id="RoomStatus" class="title is-1">{{ $t(this.getKeyAsString(Status, status) + ".title")}}</h1>

      <h2 class="subtitle is-2" v-if="status === Status.FREE">
        <b-icon icon="clock-outline" size="is-large"></b-icon>
        {{$t("room.until")}} {{meetingStart}}
      </h2>
      <h2 class="subtitle is-2" v-else-if="status !== Status.UNBOOKED">
        <b-icon icon="clock-outline" size="is-large"></b-icon>
        {{$t("room.until")}} {{meetingEnd}}
      </h2>
      <b-button
        type="is-primary is-large"
        inverted
        v-else-if="status === Status.RESERVED"
        v-on:click="status = Status.OCCUPIED"
      >{{$t("reserved.buttons.startRoom")}}</b-button>

      <b-button
        id="BtnOccupiedFind"
        v-if="status === Status.OCCUPIED"
        type="is-primary is-medium"
        inverted
        outlined
        @click="freeModal = true"
      >{{$t("reserved.buttons.find")}}</b-button>
      <b-button
        id="BtnReservedStartRoom"
        type="is-primary is-large"
        inverted
        v-else-if="status === Status.RESERVED"
        v-on:click="setStartOrEndTimeToNow('updateStart')"
      >{{$t("reserved.buttons.startRoom")}}</b-button>
      <b-button
        id="BtnFreeUseRoom"
        type="is-primary is-large"
        inverted
        v-else
        v-on:click="toggleCreateMeetingOn"
      >{{$t("free.buttons.use")}}</b-button>
    </div>
    <div class="spread">
      <div v-if="status === Status.OCCUPIED">
        <b-button
          id="BtnOccupiedEnd"
          type="is-primary is-medium"
          inverted
          outlined
          v-on:click="setStartOrEndTimeToNow('updateEnd')"
        >
          <b-icon icon="timer-off" size="is-small"></b-icon>
          {{$t("occupied.buttons.end")}}
        </b-button>
        <b-button
          id="BtnOccupiedExtend"
          type="is-primary is-medium"
          inverted
          outlined
          @click="toggleExtendModal"
          style="margin-left: 10px;"
        >
          <b-icon icon="plus-circle-outline" size="is-small"></b-icon>
          {{$t("occupied.buttons.extend")}}
        </b-button>
      </div>
      <b-button
        id="BtnReservedFind"
        v-else-if="status === Status.RESERVED"
        type="is-primary is-medium"
        inverted
        outlined
        @click="freeModal = true"
      >{{$t("reserved.buttons.find")}}</b-button>
      <b-button
        id="BtnFreeFindAnother"
        v-else
        type="is-primary is-medium"
        inverted
        outlined
        @click="freeModal = true"
      >{{$t("free.buttons.findAnother")}}</b-button>
      <div>
        <img
          class="flag"
          v-bind:src="require('../assets/countryFlags/' + getFlag())"
          @click="changeLanguage"
          alt="Language flag "
        />
        <img class="logo" src="../assets/IsaacLogo.svg" alt="Isaac Logo" v-on:click="clickLogo"/>
      </div>
    </div>
  </div>
</template>

<script>
import ExtendMeetingModal from "./ExtendMeetingModal";
import "../assets/css/styles.css";
import FreeRoom from "./FreeRoom";
import "../assets/css/styles.css";
import CreateMeeting from "./CreateMeeting";

const Status = {
  FREE: 0,
  RESERVED: 1,
  OCCUPIED: 2,
  UNBOOKED: 3
};

const Language = {
  ENGLISH: "EN.svg",
  DUTCH: "NL.svg"
};

export default {
  components: {
    CreateMeeting,
    ExtendMeetingModal,
    FreeRoom
  },
  data: function() {
    return {
      appointments: [],
      persons: null,
      timestamp: "00:00",
      meetingStart: "00:00",
      meetingEnd: "00:00",
      status: Status.FREE,
      title: Status.FREE.toString(),
      currentLanguage: Language.ENGLISH,
      date: new Date(),
      minDate: getYesterday(),
      extendModal: false,
      freeModal: false,
      modalActive: false,
      Status,
      Language,
      isAssigned: false,
      room: {
        name: "Unassigned tablet",
        location: "Unassigned tablet",
        email: "Unassigned tablet"
      },
      adminClickCounter: 0
    };
  },
  created() {
    setInterval(this.getNow, 1000);
    this.getNow();
  },
  methods: {
    toggleCreateMeetingOff() {
      this.modalActive = false;
    },
    toggleCreateMeetingOn() {
      this.modalActive = true;
    },
    getNow: function() {
      const date = new Date();
      this.timestamp = this.createTime(date);
    },
    createTime(date) {
      return (
        (date.getHours() < 10 ? "0" : "") +
        date.getHours() +
        ":" +
        (date.getMinutes() < 10 ? "0" : "") +
        date.getMinutes()
      );
    },
    changeLanguage() {
      if (this.currentLanguage === Language.ENGLISH) {
        this.currentLanguage = Language.DUTCH;
        this.$i18n.locale = "nl";
      } else {
        this.currentLanguage = Language.ENGLISH;
        this.$i18n.locale = "en";
      }
    },
    getFlag() {
      if (this.currentLanguage === Language.ENGLISH) return Language.DUTCH;
      else return Language.ENGLISH;
    },
    getKeyAsString(object, key) {
      return Object.keys(object)[key].toString().toLocaleLowerCase();
    },
    toggleExtendModal() {
      console.log(this.extendModal);
      this.extendModal = !this.extendModal;
    },
    getRoomStatus() {
      this.$apiService
        .getRoomStatus(this.room.name)
        .then(response => {
          console.log(response);
          this.status = Status[response.data.status];
          this.meetingStart = this.createTime(
            new Date(response.data.startTime * 1000)
          );
          this.meetingEnd = this.createTime(
            new Date(response.data.endTime * 1000)
          );
        })
        .catch(function() {
          let self = this;
          self.$buefy.notification.open({
            duration: 10000,
            message: `The connection to the server is lost, trying to reconnect...`,
            position: "is-bottom-right",
            type: "is-danger",
            hasIcon: true,
            closable: false
          });
        });
        this.adminClickCounter = 0;
    },
    clickLogo(){
      this.adminClickCounter++;
      if(this.adminClickCounter == 10){
        window.location.href = '/admin';
      }
    },
    setStartOrEndTimeToNow(type) {
      this.$apiService
        .updateRoomStartOrEndToNow(this.room.name, {
          type: type
        })
        .then(response => {
          console.log(response);
          if (type === "updateStart") {
            self.status = Status.OCCUPIED;
          } else {
            self.status = Status.FREE;
          }
        })
        .catch(function() {
          let self = this;
          self.$buefy.notification.open({
            duration: 10000,
            message: `The connection to the server is lost, trying to reconnect...`,
            position: "is-bottom-right",
            type: "is-danger",
            hasIcon: true,
            closable: false
          });
        });
    },
    toggleFreeModal() {
      this.freeModal = !this.freeModal;
    }
  },
  mounted: function() {
    if (!localStorage.getItem("Room-Authorization")) 
    { 
      this.$helper.showRoomIsNotAssignedDialog();
    } else {
      let self = this;
      this.$apiService.checkRoomToken().then(response => {
        if(response.status === 200) {
          this.room = response.data;
          this.$emit("roomAssigned", this.room);
          this.isAssigned = true;
          setInterval(this.getRoomStatus, 10 * 1000);
          this.getRoomStatus();
        }
        else self.$helper.showRoomInvalidAssignmentDialog();
      }).catch(function(){
        self.$helper.showRoomInvalidAssignmentDialog();
      })
    }
  }
};

function getYesterday() {
  let date = new Date();
  date.setDate(date.getDate() - 1);
  return date;
}
</script>

<style>
.modal-close {
  max-height: 80px !important;
  max-width: 80px !important;
  min-height: 80px !important;
  min-width: 80px !important;
  height: 80px !important;
  width: 80px !important;
}
</style>