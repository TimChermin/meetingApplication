<template>
    <ul class="has-padding-top">
        <li class="custom-list-item" @click="navigateTo('/admin')" >
            <div class="max-height">
               <span class="has-padding-left">
                    <b-icon icon="monitor"/>
                    Overview rooms
                </span>
            </div>
        </li>
        <li class="custom-list-item" @click="navigateTo('/admin/assign-tablet')" >
            <div class="max-height">
                <span class="has-padding-left">
                    <b-icon icon="tablet-android"/>
                    Assign tablet
                </span>
            </div>
        </li>
        <li class="custom-list-item" @click="navigateTo('/admin/edit-config')" >
            <div class="max-height">
               <span class="has-padding-left">
                    <b-icon icon="pencil"/>
                    Edit config
                </span>
            </div>
        </li>
        <li class="custom-list-item" @click="navigateTo('/admin')" >
            <div class="max-height">
               <span class="has-padding-left">
                    <b-icon icon="cogs"/>
                    Settings
                </span>
            </div>
        </li>
    </ul>
</template>

<script>
    export default {
        name: "SideMenuList",
        props:{
            title: String,
            items: Object
        },
        methods:{
            navigateTo(path){
                this.$router.push(path);
            }
        }
    }
</script>

<style scoped>
    .custom-list-item{
        font-size: 18px;
        color: whitesmoke;
        padding-bottom: 10px;
    }

    .custom-list-item :hover{
        background-color: #0084C3;
        cursor: pointer;
    }

    .has-padding-top{
        padding-top: 20px;
    }

    .has-padding-left{
        padding-left: 20px;
    }

    .max-height{
        min-height: 40px;
        display: flex;
        align-items: center;
    }
</style>