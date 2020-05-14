<template>
    <div class="main">
        <b-modal @close="backToHome" :active.sync="isModalActive">
            <form @submit.prevent="login">
                <div class="modal-card" style="width: auto">
                    <header class="modal-card-head">
                        <p class="modal-card-title"> Admin login</p>
                    </header>
                    <section class="modal-card-body">
                        <b-field label="Username">
                            <b-input
                                    type="text"
                                    placeholder="Your username"
                                    required
                                    v-model="loginData['username']">
                            </b-input>
                        </b-field>

                        <b-field label="Password">
                            <b-input
                                    type="password"
                                    password-reveal
                                    placeholder="Your password"
                                    required
                                    v-model="loginData['password']">
                            </b-input>
                        </b-field>
                        <br>
                    </section>
                    <footer class="modal-card-foot">
                        <button class="button" type="button" @click="backToHome">Close</button>
                        <button class="button is-primary">Login</button>
                    </footer>
                </div>
            </form>
        </b-modal>
    </div>
</template>

<script>
    export default {
        name: "AdminLoginModal",
        data(){
            return{
                loginData:{}
            }
        },
        props:{
            isModalActive: Boolean
        },
        methods: {
            toggleModal(){
                this.$emit("toggleLoginModal");
            },
            login(){
                this.$apiService.login(this.loginData).then((response) => {
                    console.log(response);
                    if(response.status === 200) this.logInSuccess(response);
                    else this.logInFailed();
                }).catch( () => {
                    this.logInFailed();
                });
            },
            backToHome(){
                this.toggleModal();
                if(localStorage.getItem("Authorization")) localStorage.removeItem("Authorization");
                this.$router.push("/");
            },
            logInSuccess(response){
                this.$buefy.notification.open({
                    message: `Successfully logged in`,
                    position: "is-bottom-right",
                    type: "is-success",
                    hasIcon: true,
                    closable: false
                });
                localStorage.setItem("Authorization", response.data);
                this.toggleModal();
            },
            logInFailed(){
                this.$buefy.notification.open({
                    message: `Incorrect credentials!`,
                    position: "is-bottom-right",
                    type: "is-danger",
                    hasIcon: true,
                    closable: false
                });
            }
        }
    }
</script>

<style scoped>
    /*.main {*/
    /*    background-image: url(../../../assets/IsaacBg.jpg);*/
    /*    background-position: bottom right;*/
    /*    background-size: cover;*/
    /*    background-attachment: fixed;*/
    /*    overflow-y: auto;*/
    /*    height: 100vh;*/
    /*}*/
</style>