import {shallowMount} from '@vue/test-utils'
import Main from '../../../src/components/Main'
import constants from "../constants";

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Main Reserved mode', () => {
    beforeEach(() =>{
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            //apiService,
            propsData:{
                appointments:
                    {
                        "subject": "ReservedModeEnglish",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({ status:  constants.Status.RESERVED,});
    });

    it("renders", () => {
        expect(wrapper.exists()).toBe(true);
    });
});

describe('Main Reserved mode (ENGLISH)', () => {
    beforeEach(() =>{
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            //apiService,
            propsData:{
                appointments:
                    {
                        "subject": "ReservedModeEnglish",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({status:  constants.Status.RESERVED});
    });

    it("Does button contain Start meeting, when reserved (ENGLISH)", () => {
        expect(wrapper.find("#BtnReservedStartRoom").text()).toBe("Start meeting");
    });

    it('Shows reserved when reserved (ENGLISH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("RESERVED");
    });

    it('Shows find a free room when reserved (ENGLISH)', () => {
        expect(wrapper.find('#BtnReservedFind').text()).toBe("Find a free room");
    });
});


describe('Main Reserved mode (DUTCH)', () => {
    const i18n = constants.i18nDutch

    beforeEach(() =>{
        wrapper = shallowMount(Main, {
            localVue,
            i18n
        });
        wrapper.setData({status:  constants.Status.RESERVED});
    });

    it("Does button contain Start meeting, when reserved (DUTCH)", () => {
        expect(wrapper.find("#BtnReservedStartRoom").text()).toBe("Start vergadering");
    });

    it('Shows reserved when reserved (DUTCH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("GERESERVEERD");
    });

    it('Shows find a free room when reserved (DUTCH)', () => {
        expect(wrapper.find('#BtnReservedFind').text()).toBe("Vind een vrije kamer");
    });
});