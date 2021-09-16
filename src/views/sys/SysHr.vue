<template>
    <div>
        <div style="display: flex;justify-content: center;">
            <el-input v-model="keywords"
                      size="small"
                      style="width: 400px;margin-right: 5px;"
                      placeholder="搜索用户..."
                      @keydown.enter.native="doSearch"
                      prefix-icon="el-icon-search"/>
            <el-button size="small" type="primary" icon="el-icon-search" @click="doSearch">搜索</el-button>
        </div>
        <div style="display: flex;flex-wrap: wrap;justify-content: space-between">
            <el-card class="box-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: red"
                               type="text"
                               @click="deleteHr(hr)"
                               icon="el-icon-delete">
                    </el-button>
                </div>
                <div>
                    <div class="useravatar">
                        <el-avatar size="large"
                                   :src="hr.userFace"
                                   :alt="hr.name"
                                   @error="errorHandler"/>
                    </div>
                    <div class="userinfo">
                        <ul>
                            <li>
                                <el-tag size="small" type="success">用户名</el-tag>
                                {{hr.name}}
                            </li>
                            <li>
                                <el-tag size="small" type="success">手机号码</el-tag>
                                {{hr.phone}}
                            </li>
                            <li>
                                <el-tag size="small" type="success">电话号码</el-tag>
                                {{hr.telephone}}
                            </li>
                            <li>
                                <el-tag size="small" type="success">地址</el-tag>
                                {{hr.address}}
                            </li>
                            <li>
                                <el-tag size="small" type="success">用户状态</el-tag>&nbsp;
                                <el-switch
                                        @change="enabledChange(hr)"
                                        v-model="hr.enabled"
                                        active-color="#13ce66"
                                        inactive-color="#ff4949"
                                        active-text="启用"
                                        inactive-text="禁用">
                                </el-switch>
                            </li>
                            <li style="display: flex;justify-content:right">
                                <el-tag size="small"
                                        type="success">用户角色</el-tag>
                                <div style="margin-left: 5px">
                                    <el-tag size="small"
                                            style="margin-right: 5px"
                                            v-for="(role,index) in hr.roles"
                                            :key="index">
                                        {{role.nameZh}}
                                    </el-tag>
                                    <el-popover
                                            placement="right"
                                            title="角色列表"
                                            width="200"
                                            @show="showPop(hr)"
                                            @hide="hidePop(hr)"
                                            trigger="click">
                                        <el-button icon="el-icon-more" type="text" size="small" slot="reference"></el-button>
                                        <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                            <el-option
                                                    v-for="(role,index) in allRoles"
                                                    :key="index"
                                                    :label="role.nameZh"
                                                    :value="role.id">
                                            </el-option>
                                        </el-select>
                                    </el-popover>
                                </div>
                            </li>
                            <li>
                                <el-tag size="small" type="info">备注</el-tag>
                                {{hr.remark}}
                            </li>
                        </ul>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keywords:'',
                hrs:[],
                allRoles:[],
                selectedRoles:[]
            }
        },
        methods:{
            deleteHr(hr){
                this.$confirm('此操作将永久删除'+hr.name+', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
                        if (resp) {
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doSearch(){
                this.initHrs();
            },
            errorHandler() {
                return true
            },
            initHrs(){
                this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
                    if (resp){
                        this.hrs=resp;
                    }
                })
            },
            initAllRoles(){
                this.getRequest("/system/hr/roles").then(resp=>{
                    if (resp){
                        this.allRoles=resp;
                    }
                })
            },
            showPop(hr){
                this.initAllRoles();
                let roles=hr.roles;
                this.selectedRoles=[];
                roles.forEach(role=>{
                    this.selectedRoles.push(role.id);
                })
            },
            hidePop(hr){
                let roles = [];
                Object.assign(roles, hr.roles);
                let flag = false;
                if (roles.length != this.selectedRoles.length) {
                    flag = true;
                } else {
                    for (let i = 0; i < roles.length; i++) {
                        let role = roles[i];
                        for (let j = 0; j < this.selectedRoles.length; j++) {
                            let sr = this.selectedRoles[j];
                            if (role.id == sr) {
                                roles.splice(i, 1);
                                i--;
                                break;
                            }
                        }
                    }
                    if (roles.length != 0) {
                        flag = true;
                    }
                }
                if (flag){
                    let url='/system/hr/roles?hrid='+hr.id;
                    this.selectedRoles.forEach(sr=>{
                        url+="&rids="+sr;
                    })
                    this.putRequest(url).then(resp=>{
                        if (resp){
                            this.initHrs();
                        }
                    })
                }
            },
            enabledChange(hr){
                delete hr.roles;
                this.putRequest("/system/hr/",hr).then(resp=>{
                    if (resp){
                        this.initHrs();
                    }
                })
            }
        },
        mounted() {
            this.initHrs();
        }
    }
</script>

<style scoped>
    .box-card {
        width: 300px;
        margin: 20px auto;
    }
    .useravatar{
        display: flex;
        justify-content: center;
        margin-bottom: 10px;
    }
    .useravatar .el-avatar{
        cursor: pointer;
        width: 48px;
        height: 48px;
        line-height: 48px;
    }
    .userinfo ul{
        font-size: 13px;
        list-style: none;
        padding-left: 10px;
    }
    .userinfo ul li{
        margin-bottom: 5px;
    }
</style>