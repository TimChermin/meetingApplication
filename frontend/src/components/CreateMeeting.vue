<template>
  <b-modal :active.sync="isActive" full-screen @close="toggleOff">
    <div id="container">
      <div class="has-text-grey-dark reserve-title">{{$t("reserveRoom.title")}} {{roomName}}</div>
      <div class="element">
        <div class="reserve-text">{{$t("reserveRoom.description")}}</div>
        <b-button
          v-for="time in times"
          :key="time.id"
          @click="changeTimeSelection(time.id)"
          class="buttonTwo groove is-large margin-right"
          v-bind:class="{'selected-button' : isSelected(time.id)}"
        >{{time.time}}</b-button>
        <div class="error" v-show="timeError">{{$t("reserveRoom.secondDescription")}}</div>
      </div>
      <div class="element">
        <div class="reserve-text">{{$t("reserveRoom.secondDescription")}}</div>
        <div class="test">
          <b-input v-model="name" @input="updateNameList" id="reserve-textfield"></b-input>
          <div class="error" v-show="nameError">{{$t("reserveRoom.invalidName")}}</div>
        </div>
        <br />
        <select
          v-bind:size="filteredNames.length + 1"
          class="reserve-select"
          v-model="selectedName"
          v-bind:hidden="!showSelect"
          @change="selectChanged"
        >
          <option
            v-for="name in filteredNames"
            :key="name.id"
            v-bind:value="name.alias"
            expanded
          >{{name.alias}}</option>
        </select>
      </div>
      <div id="button-container">
        <b-button class="extendButton is-large margin-right" @click="startMeeting()">{{$t("reserveRoom.startMeeting")}}</b-button>
        <b-button class="is-large groove buttonTwo margin-both" @click="toggleOff">{{$t("reserveRoom.cancel")}}</b-button>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: "CreateMeeting",
  props: {
    roomName: String,
    isActive: Boolean
  },
  methods: {
    updateNameList() {
      if (this.name !== "") {
        this.showSelect = true;
        if (this.namesContainsName(this.name)) this.nameError = false;
      }
      this.showSelect = this.name !== "" && this.filteredNames().length > 0;
    },
    selectChanged() {
      if (this.selectedName !== "" || this.selectedName == null) {
        this.name = this.selectedName;
        this.nameError = false;
      }
    },
    toggleOff() {
      this.$emit("toggleOff");
    },
    changeTimeSelection(id) {
      this.selectedTime = id;
      this.timeError = false;
    },
    getUserByName(nameToLookFor) {
      return this.names.filter((name) => name.alias.toString() === nameToLookFor)[0];
    },
    isSelected(id) {
      return this.selectedTime === id;
    },
    startMeeting() {
      if (this.namesContainsName(this.name)) {
        if (this.selectedTime !== -1) {
          console.log("information correct");

          this.roomData.endDate = new Date(this.roomData.startDate.getTime() + this.times[this.selectedTime].timeAsInt*60000);
          this.roomData.userEmail = this.getUserByName(this.name).address;
          this.roomData.subject = "Meeting created by " + this.name.toString(); 
          this.$apiService.reserveFreeRoom(this.roomName, this.roomData);
          this.$emit("toggleOff");
        } else {
          console.log("Time not selected");
          this.timeError = true;
        }
      } else {
        console.log("Name not found");
        this.nameError = true;
      }
    },
    namesContainsName(name) {
      for (var i = 0; i < this.names.length; i++) {
        if (this.names[i].alias === name) return true;
      }
      return false;
    }
  },
  computed: {
    filteredNames: function() {
      return this.names.filter((name) => name.alias.toString().toUpperCase().includes(this.name.toUpperCase()));
    }
  },
  mounted: function() {
    this.$apiService.getAllUserNames().then(result => {
      console.log(result.data);
      this.names = result.data;
      console.log(result);
      console.log(this.names)
    }) .catch(e => {
      console.log(e);
    });
  },
  data() {
    return {
      name: "",
      showArray: this.names,
      selectedName: "",
      showSelect: false,
      selectedTime: -1,
      nameError: false,
      timeError: false,
      names : [],
      times: [
        {
          time: "15 MIN",
          timeAsInt: 15,
          id: 0
        },
        {
          time: "30 MIN",
          timeAsInt: 30,
          id: 1
        },
        {
          time: "45 MIN",
          timeAsInt: 45,
          id: 2
        },
        {
          time: "1 HOUR",
          timeAsInt: 60,
          id: 3
        }
      ],
      roomData : {
        roomName : this.roomName.toString(),
        startDate : new Date,
        endDate : new Date,
        userEmail : "",
        subject : ""
      }
    };
  }
};
</script>

<style>
select option{
  color:black;
}
.error {
  color: red;
}
.test {
  position: relative;
  width: 40%;
}
.test input {
  color: cornflowerblue;
  font-size: 1.2rem;
  caret-color: black;
}
.test:before {
  border-style: solid;
  border-width: medium 0 0 0;
  border-color: rgba(0, 0, 0, 0.42);
  bottom: -1px;
  content: "";
  left: 0;
  position: absolute;
  width: 100%;
}
#reserve-textfield {
  line-height: 20px;
  border-style: none;
  border: none;
  background-color: transparent;
  box-shadow: none;
}
.margin-right {
  margin-right: 0.6%;
}
.margin-both {
  margin-right: 0.3%;
  margin-left: 0.3%;
}
#button-container {
  position: absolute;
  bottom: 8%;
  width: 100%;
}
.element {
  margin-top: 3%;
  margin-bottom: 3%;
}
.extendButton {
  align-self: flex-end;
  position: relative;
  background-color: #2fa1ed !important;
  color: white !important;
  border: darkgray 1px !important;
}
.extendButton:hover {
  color: ghostwhite !important;
  border-color: white !important;
}
.selected-button {
  border-color: cornflowerblue !important;
  color: #363636 !important;
}
.extendButton:focus {
  border-color: cornflowerblue !important;
  color: #363636 !important;
}
#container {
  padding: 6%;
}
.reserve-text {
  font-size: 1.3rem;
  margin-top: 1%;
  margin-bottom: 1.5%;
  color: black !important;
}
.reserve-title {
  font-size: 55px;
  font-weight: 500;
}
.reserve-select {
  width: 40%;
  font-size: 20px;
}
.buttonTwo:hover {
  color: cornflowerblue !important;
  border-color: cornflowerblue !important;
}

.buttonTwo:active {
  color: cornflowerblue !important;
  border-color: cornflowerblue !important;
}
.buttonTwo:focus,
.button.is-focused {
  border-color: cornflowerblue !important;
  color: #363636 !important;
}
.groove {
  border-style: groove;
  border-color: rgba(115, 115, 115, 0.77);
  border-width: 3px !important;
}
</style>