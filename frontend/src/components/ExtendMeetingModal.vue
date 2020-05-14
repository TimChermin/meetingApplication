<template>
    <b-modal @close="toggleModal" class="is-paddingless" :active.sync="extendMeetingModal">
        <div class="card is-centered">
            <div class="is-half is-offset-one-quarter modalCenter is-marginless">
                <div id="ExtendModalTitle" class="extendTitle title has-text-grey-dark titlePosition">{{$t("extendModal.title")}}</div>
                <div class="Description is-left ">{{$t("extendModal.description")}}</div>
                <div class="timeBoxes columns is-mobile">
                    <b-button class="column buttonTwo groove is-large" v-on:click="selectedExtendTime = 15">15 MIN</b-button>
                    <b-button class="column buttonTwo groove is-large" v-on:click="selectedExtendTime = 30">30 MIN</b-button>
                    <b-button class="column buttonTwo groove is-large" v-on:click="selectedExtendTime = 45">45 MIN</b-button>
                    <b-button class="column buttonTwo groove is-large" v-on:click="selectedExtendTime = 60">1 {{$t("extendModal.hour")}}</b-button>
                </div>
                <b-button v-on:click="toggleModal" type="isinfo" class="extendButton is-flex is-large   ">{{$t("extendModal.button")}}</b-button>
            </div>
        </div>
    </b-modal>
</template>

<script>

export default {
    name: "ExtendMeetingModal",
    props: {
        extendMeetingModal: {
          type: Boolean,
        },
        roomName: {
            roomName: ['roomName']
        }
        },
    data: function() {
        return {
            selectedExtendTime: 1,
        };
    },
  methods: {
    toggleModal() {
      this.$emit("toggleModal", this.extendMeetingModal);
      this.extendMeeting()
    },
      extendMeeting() {
        console.log(this.roomName);
          this.$apiService.extendMeeting(this.roomName,
              {
                  extendTime: this.selectedExtendTime,
                  type: "updateEnd"
              }
          )
              .then(response => {
                  console.log(response);
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
  }
};
</script>
<style scoped>
.columns {
  margin: 0 !important;
  padding: 3%;
}

.extendTitle {
  text-align: left;
  color: #adadad;
}

.titlePosition {
  padding-left: 3%;

  padding-top: 3%;
  font-size: x-large;
}

.groove {
  border-style: groove;
  border-color: rgba(115, 115, 115, 0.77);
  border-width: 3px !important;
}

.column {
  margin-right: 3% !important;
    height: 50px;
}

.timeBlock {
  display: block;
}

.Description {
  display: flex;
  justify-content: space-between;
  padding-left: 3%;
  padding-top: 10%;
  font-size: 1.3rem;
}

.modalCenter {
  display: block;

  /* Probably need media queries here */
  width: 59% !important;
  max-width: 100%;

  height: 60% !important;
  max-height: 100%;

  position: fixed;

  z-index: 100;

  left: 37%;
  top: 50%;
  transform: translate(-50%, -50%);
  background: white !important;
}

@media only screen and (max-width: 1080px) {
  .modalCenter {
    height: 40%;
    width: 70% !important;

    margin-right: 12% !important;
    font-size: 0.7rem;
  }
}

@media only screen and (max-width: 800px) {
  .modalCenter {
    height: 45%;
    width: 85% !important;
    left: 50% !important;
    font-size: 0.7rem;
  }
}
@media only screen and (max-height: 800px) {
  .modalCenter {
    height: 50%;
    width: 59% !important;
    left: 38% !important;
    font-size: 0.7rem;
  }
  .extendButton {
    top: 10% !important;
  }
}
@media only screen and (min-height: 1000px) {
  .modalCenter {
    height: 50% !important;
    width: 70% !important;
    left: 38% !important;
    font-size: 0.7rem;
  }
  .extendButton {
    top: 15% !important;
    font-size: 1.2rem !important;
  }
}

.extendButton {
  align-self: flex-end;
  position: relative;
  top: 10% !important;
  left: 3%;
  background-color: #2fa1ed !important;
  color: white !important;
  border: darkgray 1px !important;
}
.extendButton:hover {
  color: ghostwhite !important;
  border-color: white !important;
}
.extendButton:focus,
.button.is-focused {
  border-color: cornflowerblue !important;
  color: #363636 !important;
}

.timeBoxes {
  text-align: center !important;
}

.timeBoxes button {
  line-height: 0;
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
</style>