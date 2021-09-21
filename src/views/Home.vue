<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title" @click="goHome">微人事</div>
                <div style="display: flex;align-items: center;">
                    <el-button icon="el-icon-message-solid" type="text" style="display: flex;color: white;font-size: 15px" @click="goChat"></el-button>
                    <span style="margin: 0 10px;cursor: pointer">{{user.name}}</span>
                    <el-dropdown class="el-dropdown" @command="commandHandler">
                        <span class="el-dropdown-link">
                            <i><img :src="user.userFace"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">设置</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside style="border-right: 1px solid #EBEEF5;">
                    <el-menu router unique-opened>
                        <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i :class="item.iconCls" style="color: #409EFF;margin-right:10px;"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,index) in item.children" :key="index">
                                <i :class="child.iconCls" style="color: #409EFF;margin-right:10px;"></i>
                                <span>{{child.name}}</span>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                    <el-container>
                        <el-scrollbar style="height:610px;flex-grow:1;" wrap-style="overflow-x:hidden;">
                            <el-main>
                                <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                                    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                                    <el-breadcrumb-item><span style="font-weight: bold">{{this.$router.currentRoute.name}}</span></el-breadcrumb-item>
                                </el-breadcrumb>
                                <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                                    我吊你啊！
                                </div>
                                <router-view class="homeRouterView"/>
                            </el-main>
                        </el-scrollbar>
                    </el-container>
            </el-container>
            <el-footer style="background-color: #f7fbfd;display: flex;align-items: center;justify-content:center;font-size: 12px;color: #cac6c6">翻斗大街翻斗花园二号楼1001室&copy;孔明灯</el-footer>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                user: JSON.parse(window.sessionStorage.getItem("user"))
            }
        },
        computed:{
            routes(){
                return this.$store.state.routes;
            }
        },
        methods: {
            goHome(){
                this.$router.push("/home");
            },
            goChat(){
              this.$router.push("/chat");
            },
            commandHandler(cmd) {
                if (cmd == 'logout') {
                    this.$confirm('确定退出吗?', '', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.getRequest("logout");
                        window.sessionStorage.removeItem("user");
                        this.$store.commit("initRoutes",[])
                        this.$router.replace("/");
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消退出'
                        });
                    });
                }else if (cmd == 'userinfo') {
                    this.$router.push('/hrinfo');
                }
            }
        }
    }
</script>

<style scoped>
    .homeWelcome{
        text-align: center;
        font-size: 30px;
        font-family: 华文行楷;
        color: #409EFF;
        padding-top: 50px;
    }
    .homeRouterView{
        margin-top: 20px;
    }
    .homeHeader {
        background-color: #409EFF;
        display: flex;
        align-items: center; /*垂直居中*/
        justify-content: space-between;
        padding: 0 20px;
        box-sizing: border-box;
    }

    .homeHeader .title {
        font-size: 30px;
        font-family: 华文行楷;
        color: white;
        cursor: pointer;
    }

    .el-dropdown {
        cursor: pointer;
    }

    .el-dropdown-link img {
        display: flex;
        width: 48px;
        height: 48px;
        border-radius: 48px;
        margin: 0 24px 0 0;
    }
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
        text-align: center;
    }
</style>