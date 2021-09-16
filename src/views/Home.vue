<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">微人事</div>
                <el-dropdown class="el-dropdown" @command="commandHandler">
                <span class="el-dropdown-link">
                    <i><img :src="user.userFace"></i>
                </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>{{user.name}}</el-dropdown-item>
                        <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                        <el-dropdown-item command="setting">设置</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
            <el-container>
                <el-aside style="border-right: 1px solid #EBEEF5">
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
            <el-footer style="background-color: #f7fbfd;display: flex;align-items: center;justify-content:center;font-weight: bold;">&copy;孔明灯</el-footer>
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
    }

    .el-dropdown {
        cursor: pointer;
        text-align: center;
    }

    .el-dropdown-link img {
        width: 48px;
        height: 48px;
        border-radius: 48px;
        display: flex;
        align-items: center; /*垂直居中*/
        margin: 0 31px;
    }
    .block{
        width: 340px;
        height: 150px;
        padding: 26px;
        float: left;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
        text-align: center;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
</style>