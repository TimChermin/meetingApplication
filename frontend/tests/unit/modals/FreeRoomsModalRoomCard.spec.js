import {shallowMount} from '@vue/test-utils'
import FreeRoomsRoomCard from '../../../src/components/Room'
import constants from "../constants";

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Free meeting rooms modal, RoomCard UNBOOKED (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(FreeRoomsRoomCard, {
            localVue,
            i18n,
            propsData:{
                room: {
                    "status": "UNBOOKED",
                    "subject": null,
                    "startTime": 0,
                    "endTime": 0,
                    "name": "Newton",
                    "location": "2nd Floor"
                },
            }
        });
    });

    it('RoomCard contains people (ENGLISH)', () => {
        expect(wrapper.find("#RoomPeopleTemplate").text()).toContain("people");
    });

    /*it('Room location (ENGLISH)', () => {
        expect(wrapper.find("#RoomLocation").text()).toContain("Floor");
    });*/

    it('Room unbooked when not FREE (ENGLISH)', () => {
        expect(wrapper.find("#RoomUnbookedDiv").text()).toContain("Room not booked");
    });

    it('Room reserve button has the english text (ENGLISH)', () => {
        expect(wrapper.find("#RoomBtnReserve").text()).toContain("Reserve");
    });
});


describe('Free meeting rooms modal, RoomCard (DUTCH)', () => {
    const i18n = constants.i18nDutch;

    beforeEach(() => {
        wrapper = shallowMount(FreeRoomsRoomCard, {
            localVue,
            i18n,
            propsData:{
                room: {
                    "status": "UNBOOKED",
                    "subject": null,
                    "startTime": 0,
                    "endTime": 0,
                    "name": "Newton",
                    "location": "2nd Floor"
                },
            }
        });
    });

    it('RoomCard contains people (DUTCH)', () => {
        expect(wrapper.find("#RoomPeopleTemplate").text()).toContain("mensen");
    });

    /*it('Room location (ENGLISH)', () => {
        expect(wrapper.find("#RoomLocation").text()).toContain("Floor");
    });*/

    it('Room unbooked when not FREE (DUTCH)', () => {
        expect(wrapper.find("#RoomUnbookedDiv").text()).toContain("Kamer niet geboekt");
    });

    it('Room reserve button has the dutch text (Dutch)', () => {
        expect(wrapper.find("#RoomBtnReserve").text()).toContain("Reserveren");
    });
});



describe('Free meeting rooms modal, RoomCard FREE (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(FreeRoomsRoomCard, {
            localVue,
            i18n,
            propsData:{
                room: {
                    "status": "FREE",
                    "subject": null,
                    "startTime": 0,
                    "endTime": 0,
                    "name": "Newton",
                    "location": "2nd Floor"
                },
            }
        });
    });

    it('Room unbooked when not FREE (ENGLISH)', () => {
        expect(wrapper.find("#RoomFreeDiv").text()).toContain("Until");
    });
});

describe('Free meeting rooms modal, RoomCard FREE (DUTCH)', () => {
    const i18n = constants.i18nDutch;

    beforeEach(() => {
        wrapper = shallowMount(FreeRoomsRoomCard, {
            localVue,
            i18n,
            propsData:{
                room: {
                    "status": "FREE",
                    "subject": null,
                    "startTime": 0,
                    "endTime": 0,
                    "name": "Newton",
                    "location": "2nd Floor"
                },
            }
        });
    });

    it('Room unbooked when not FREE (DUTCH)', () => {
        expect(wrapper.find("#RoomFreeDiv").text()).toContain("Tot");
    });
});
