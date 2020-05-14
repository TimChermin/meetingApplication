import {shallowMount} from '@vue/test-utils'
import FreeRooms from '../../../src/components/FreeRoom'
import constants from "../constants";

const localVue = constants.localVue;
const i18n = constants.i18n;
let wrapper;

describe('Free meeting rooms modal (ENGLISH)', () => {
    beforeEach(() => {
        wrapper = shallowMount(FreeRooms, {
            localVue,
            i18n
        });
    });

    it('Free rooms header should show the right text (ENGLISH)', () => {
        expect(wrapper.find("#FreeRoomsHeader").text()).toBe("Free meeting rooms");
    });
});

describe('Free meeting rooms modal (DUTCH)', () => {
    const i18n = constants.i18nDutch

    beforeEach(() => {
        wrapper = shallowMount(FreeRooms, {
            localVue,
            i18n
        });
    });

    it('Free rooms header should show the right text (DUTCH)', () => {
        expect(wrapper.find("#FreeRoomsHeader").text()).toBe("Vrije vergaderruimte");
    });
});