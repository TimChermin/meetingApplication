import { NotificationProgrammatic as notification } from 'buefy'
import { DialogProgrammatic as dialog } from 'buefy'

export const popupMessage = (message, type) =>{
    notification.open({
        duration: 5000,
        message: message,
        position: "is-bottom-right",
        type: type,
        hasIcon: true,
        closable: false
    });
};

export const showRoomIsNotAssignedDialog = () => {
    dialog.alert({
        title: "Error - Not assigned",
        message:
            "This device is currently not assigned. Please ask the admin to login and assign this device to a room.",
        type: "is-danger",
        hasIcon: true,
        ariaRole: "alertdialog",
        ariaModal: true,
        canCancel: false,
        confirmText: 'Log in to admin panel',
        onConfirm: function(){
            window.location.href = "/admin"
        }
    });
};

export const showRoomInvalidAssignmentDialog = () => {
    dialog.alert({
        title: "Error - Assignment Invalid",
        message:
            "This device is assigned to a room, but it could not be verified correctly. Please ask the admin to login and re-assign this device to a room.",
        type: "is-danger",
        hasIcon: true,
        ariaRole: "alertdialog",
        ariaModal: true,
        canCancel: true,
        confirmText: 'Log in to admin panel',
        cancelText: 'Retry',
        onConfirm: function(){
            window.location.href = "/admin"
        },
        onCancel: function(){
            window.location.href = '';
        }
    });
};