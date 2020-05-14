<template>
  <div>
    <b-modal @close="closeModal" :active.sync="isModalActive" full-screen>
      <CreateMeeting v-bind:isActive="modalActive" v-bind:roomName="selectedRoomName" v-on:toggleOff="toggleOff"/>
      <div class="main">
        <h1 id="FreeRoomsHeader" class="title is-1 title-freeroom">{{$t('freeMeetingRooms.title')}}</h1>
        <div class="columns">
          <div v-bind:key="room.id" v-for="room in this.freeRooms">
            <div class="roomcard">
              <Room v-bind:room="room" v-on:toggleCreateMeetingModal="activateModal(room.name)"/>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
import Room from "./Room";
import { AXIOS } from "../../http-common";
import CreateMeeting from "./CreateMeeting"
export default {
  name: "FreeRoom",
  components: {
    Room,
    CreateMeeting
  },
  props: {
    isModalActive:Boolean
  },
  methods: {
    closeModal() {
      this.$emit("closeModal", false);
    },
    showModal() {
      this.isModalActive = true;
      this.getFreeRooms();
    },
    toggleOff() {
      this.modalActive = false;
    },
    getFreeRooms() {
      let self = this;
      AXIOS.get(`rooms/free`)
        .then(response => {
          console.log(response);
          this.freeRooms = response.data;
        })
        .catch(e => {
          this.errors.push(e);
        })
        .finally(function () {
          self.loadingComponent.close();
        });
    },
    activateModal(roomName) {
      this.modalActive = true;
      this.selectedRoomName = roomName;
    }
  },
  data() {
    return {
      freeRooms: [],
      loadingComponent: {},
      modalActive: false,
      selectedRoomName: ""
    };
  },
  watch: {
    isModalActive: function(newVal) {
      if (newVal) {
        this.getFreeRooms();
        this.loadingComponent = this.$buefy.loading.open();
      }
    }
  }
};
</script>

<style scoped>
  .title.is-1 {
    font-size: 4rem;
  }
  .roomcard {
    display: inline-block;
    margin: 16px;
  }
  .title-freeroom {
    margin: 15px !important;
  }
  .columns {
    width: 100% !important;
    flex-wrap: wrap;
    max-height: calc(100vh - 72px - 30px);
    overflow-y: auto;
  }

  .columnfix {
    background-color: transparent !important;
  }

  .main {
    background-image: url(../assets/IsaacBg.jpg);
    background-position: bottom right;
    background-size: cover;
    background-attachment: fixed;
    overflow-y: auto;
    height: 100vh;
  }

  .is-large.modal-close {
    max-height: 80px !important;
    max-width: 80px !important;
    min-height: 80px !important;
    min-width: 80px !important;
    height: 80px !important;
    width: 80px !important;
  }

  .modal-close {
    max-height: 80px !important;
    max-width: 80px !important;
    min-height: 80px !important;
    min-width: 80px !important;
    height: 80px !important;
    width: 80px !important;
  }
</style>
