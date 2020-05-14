import {shallowMount} from '@vue/test-utils'
import ExtendMeeting from '../../../src/components/ExtendMeetingModal'
import constants from "../constants";

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Free meeting rooms modal, RoomCard (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(ExtendMeeting, {
            localVue,
            i18n,
        });
    });

    /*it('Room location (ENGLISH)', () => {
        expect(wrapper.find("#RoomLocation").text()).toBe("Floor");
    });*/

    it('Room location (ENGLISH)', () => {
        expect(wrapper.find("#ExtendModalTitle").text()).toContain("Extend Meeting");
    });
});
