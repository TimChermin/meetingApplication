<template>
    <div class="column is-one-quarter" style="padding: 0">
        <v-calendar
                ref="calendar"
                v-model="value"
                :type="type"
                :events="events"
                :event-overlap-mode="mode"
                :event-overlap-threshold="30"
                :interval-height="264"
                :event-color="getEventColor"
                @change="getEvents"
        ></v-calendar>
    </div>
</template>

<script>
    export default {
        data: () => ({
            type: "day",
            mode: "stack",
            modes: ["stack", "column"],
            value: "",
            events: []
        }),
        props: {
            room: Object
        },
        mounted() {
            setInterval(this.update, 12000);
            setTimeout(this.update, 2000);
        },
        methods: {
            update: function () {
                let self = this;
                let date = new Date();
                this.$refs.calendar.scrollToTime({hour: date.getHours(), minute: date.getMinutes()})
                this.$apiService
                    .GetARoomAppointment(this.room.name)
                    .then(response => {
                        let newEvents = [];
                        console.dir(response.data);
                        console.dir("jo");
                        console.dir(self.room);
                        response.data.forEach(event => {
                            let startDate = this.formatDate(new Date(event.startTime * 1000), true);
                            let endDate = this.formatDate(new Date(event.endTime * 1000), true);
                            newEvents.push({
                                name: event.subject,
                                start: startDate,
                                end: endDate,
                                color: this.getColorBasedOnTime(new Date(event.startTime * 1000), new Date(event.endTime * 1000))
                            });
                        });
                        this.events = newEvents;
                    })
                    .catch(function (error) {
                        console.log("Error while fetching sidebar data - " + error);
                    });
            },
            getEventColor(event) {
                return event.color;
            },
            formatDate(a, withTime) {
                return withTime
                    ? `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`
                    : `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()}`;
            },
            getColorBasedOnTime(start, end) {
                let reservedTime = new Date(start.getTime - 10 * 60000);
                let startTime = start.getTime();
                let endTime = end.getTime();
                if (Date.now() >= startTime && Date.now() <= endTime) {
                    console.dir("In Occupied")
                    return "#e31029";
                }
                else if (Date.now() >= reservedTime.getTime() && Date.now() <= startTime) {
                    console.dir("In Reserved")
                    return "#f36c08";
                }   else if (Date.now() > startTime) {
                    console.dir("In Expired")
                    return "#8b8989";
                } else {
                    console.dir("In Planned")
                    return "#3b9cdf"
                }

            }
        }
    }
</script>
<style>
    .v-event-timed {
        padding: 8px !important;
    }

    .white--text div,
    .white--text div strong {
        color: #ffffff !important;
    }
</style>