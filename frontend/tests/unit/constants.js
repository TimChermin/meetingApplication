import {createLocalVue} from "@vue/test-utils";
import VueI18n from "vue-i18n";
import messages from "../../src/assets/translations";
import Buefy from 'buefy'
import { config } from '@vue/test-utils'

config.mocks['$apiService'] = {
    getRoomStatus: config.methods['getRoomStatus'] = () => {return new Promise(() => {return 'test'});},
    getRoomAppointments: config.methods['getRoomAppointments'] = () => {return new Promise(() => {return 'test'});}
};

config.mocks['$helper'] = {
    showRoomIsNotAssignedDialog: config.methods['showRoomIsNotAssignedDialog'] = () => {return  'do nothing'},
    popupMessage: config.methods['popupMessage'] = () => {return  'do nothing'},
    showRoomInvalidAssignmentDialog: config.methods['showRoomInvalidAssignmentDialog'] = () => {return  'do nothing'}
};

const localVue = createLocalVue();
localVue.use(VueI18n);
localVue.use(Buefy);

const i18n = new VueI18n({
    locale: 'en', // set locale
    messages // set locale messages
});

const i18nDutch = new VueI18n({
    locale: 'nl', // set locale
    messages // set locale messages
});

export default {
    localVue,
    i18n,
    i18nDutch,
    Status: {FREE: 0, RESERVED: 1, OCCUPIED: 2}
}
