<template>
    <div>
        <el-form :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
            <h1 class="loginTitle">用户登录</h1>
            <el-form-item prop="username">
                <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item>
                <el-checkbox size="normal" v-model="checked">记住密码</el-checkbox>
            </el-form-item>
            <el-button size="normal" type="primary" class="loginButton" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                loginForm: {
                    username: 'admin',
                    password: '123'
                },
                checked: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                }
            }
        },
        methods: {
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
                            if (resp) {
                                window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
                                let path = this.$route.query.redirect;
                                this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                            }
                        })
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 150px auto;
        width: 300px;
        padding: 25px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 10px auto 20px auto;
        text-align: center;
        color: #505458;
    }

    .loginButton {
        width: 100%;
    }
</style>