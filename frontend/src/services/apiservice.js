import { AXIOS } from "../../http-common";

export const reserveFreeRoom = (roomName, data) => {
    return postRequest(`rooms/${roomName}`, data);
};
export const updateRoomStartOrEndToNow = (roomName, data) => {
    return putRequest(`rooms/${roomName}/updateStartOrEnd`, data);
};
export const extendMeeting = (roomName, data) => {
    return putRequest(`rooms/${roomName}/extendMeeting`, data);
};
export const getAllUserNames = () => {
    return getRequest('users')
}
export const getFreeRooms = () => {
    return getRequest(`rooms/free`);
};
export const getRoomStatus = (roomName) => {
    return getRequest(`rooms/${roomName}/status`);
};
export const getRoomAppointments = () => {
    return getRequest(`rooms/appointments`);
};
export const getRooms = () => {
    return getRequest(`rooms`);
};
export const getRoomLists = () => {
    return getRequest(`rooms/lists`);
};
export const GetARoomAppointment = (roomName) => {
    return getRequest(`rooms/${roomName}/allappointments`);
};
export const login = (userData) => {
    return postRequest('/login', userData);
};
export const assignRoom = (room, JWT) => {
    room.JWT = JWT;
    console.log(room);
    return postRequest('/rooms/assign', room);
};
export const checkRoomToken = () => {
    return AXIOS.get('/rooms/me', {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Content-type': 'application/json',
            'Authorization': localStorage.getItem("Room-Authorization")
        },
    }).then((response) => {
        return response;
    }).catch(e => {
        console.log(e);
    });
};

const getRequest = (url) => {

    return AXIOS.get(`${url}`, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Content-type': 'application/json'
        },
    }).then((response) => {

        return response;
    })
        .catch(e => {
            console.log(e);
        });
};
const postRequest = (url, data) => {
    return AXIOS.post(url, data)
};
const putRequest = (url, data) => {
    return AXIOS.put(url, data)
};


