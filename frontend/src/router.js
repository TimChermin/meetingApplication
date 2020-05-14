import Vue from 'vue'
import Router from 'vue-router'
import Home from "./components/Home";
import AdminPage from "./components/AdminPage";
import OverviewRooms from "./components/dashboard_components/OverviewRooms";
import AssignTablet from "./components/dashboard_components/AssignTablet";
import EditConfig from "./components/dashboard_components/EditConfig";

Vue.use(Router);

export default new Router({
    mode: 'history',
    hash: false,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/admin',
            name: 'admin',
            component: AdminPage,
            children: [
                {
                    path: '',
                    component: OverviewRooms
                },
                {
                    path: 'assign-tablet',
                    component: AssignTablet
                },
                {
                    path: 'edit-config',
                    component: EditConfig
                }
            ]
        }
    ]
});