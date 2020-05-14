import {shallowMount} from '@vue/test-utils'
import Main from '../../../src/components/Main'
import constants from "../constants";

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Main Occupied Mode', () => {
    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            propsData:{
                appointments:
                    {
                        "subject": "OccupiedModeEnglish",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({ status: constants.Status.OCCUPIED,});
    });

    it("renders", () => {
        expect(wrapper.exists()).toBe(true);
    });
});

describe('Main Occupied Mode (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
           // apiService,
            propsData:{
                appointments:
                    {
                        "subject": "OccupiedModeEnglish",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({ status: constants.Status.OCCUPIED,});
    });

    it("Does button contain Find a free room, when Occupied (ENGLISH)", () => {
        expect(wrapper.find('#BtnOccupiedFind').text()).toBe("Find a free room");
    });

    it('Shows Occupied when Occupied (ENGLISH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("OCCUPIED");
    });

    it('Shows find a free room when reserved (ENGLISH)', () => {
        expect(wrapper.find("#BtnOccupiedEnd").text()).toBe("End");
    });

    it('Shows find a free room when reserved (ENGLISH)', () => {
        expect(wrapper.find("#BtnOccupiedExtend").text()).toBe("Extend");
    });
});

describe('Main Occupied Mode (DUTCH)', () => {
    const i18n = constants.i18nDutch

    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            //apiService,
            propsData:{
                appointments:
                    {
                        "subject": "OccupiedModeDutch",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({ status: constants.Status.OCCUPIED,});
    });

    it("Does button contain Find a free room, when Occupied (DUTCH)", () => {
        expect(wrapper.find('#BtnOccupiedFind').text()).toBe("Vind een vrije kamer");
    });

    it('Shows Occupied when Occupied (DUTCH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("BEZET");
    });

    it('Shows find a free room when reserved (DUTCH)', () => {
        expect(wrapper.find("#BtnOccupiedEnd").text()).toBe("Beëndigen");
    });

    it('Shows find a free room when reserved (DUTCH)', () => {
        expect(wrapper.find("#BtnOccupiedExtend").text()).toBe("Verlengen");
    });
});