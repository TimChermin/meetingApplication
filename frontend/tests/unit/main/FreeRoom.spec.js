import {shallowMount} from '@vue/test-utils'
import Main from '../../../src/components/Main'
import constants from "../constants"

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Main Free Mode', () => {
    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
        });

        wrapper.setData({
            status: constants.Status.FREE,});
    });

    it("renders", () => {
        expect(wrapper.exists()).toBe(true);
    });
});

describe('Main Free Mode (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            propsData:{
                appointments:
                    {
                        "subject": "FreeModeEnglish",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({
            status: constants.Status.FREE,});
    });

    it("Does button contain Use meeting room, when Free (ENGLISH)", () => {
        expect(wrapper.find("#BtnFreeUseRoom").text()).toBe("Use meeting room");
    });

    it('Shows free when free (ENGLISH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("FREE");
    });

    it('Shows find another free room when free (ENGLISH)', () => {
        expect(wrapper.find('#BtnFreeFindAnother').text()).toBe("Find another free room");
    });
});



describe('Main Free Mode (DUTCH)', () => {
    const i18n = constants.i18nDutch;

    beforeEach(() => {
        wrapper = shallowMount(Main, {
            localVue,
            i18n,
            propsData:{
                appointments:
                    {
                        "subject": "FreeModeDutch",
                        "startTime": "2020-04-01T11:00:00.000+0000",
                        "endTime": "2020-04-01T12:00:00.000+0000",
                        "location": "Newton"
                    },
            }
        });
        wrapper.setData({
            status: constants.Status.FREE,});
    });

    it("Does button contain Use meeting room, when Free (DUTCH)", () => {
        expect(wrapper.find("#BtnFreeUseRoom").text()).toBe("Gebruik kamer");
    });

    it('Shows free when free (DUTCH)', () => {
        expect(wrapper.find("#RoomStatus").text()).toBe("VRIJ");
    });

    it('Shows find another free room when free (DUTCH)', () => {
        expect(wrapper.find('#BtnFreeFindAnother').text()).toBe("Vind een andere vrije kamer");
    });
});
