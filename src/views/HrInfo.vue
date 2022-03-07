<template>
    <div v-if="hr" style="display: flex;justify-content: center">
        <el-card class="box-card" style="width: 400px;">
            <div slot="header" class="clearfix">
                <span>{{hr.name}}</span>
            </div>
            <div>
                <div style="display: flex;justify-content: center">
                    <el-upload
                            :show-file-list="false"
                            :on-success="onSuccess"
                            :data="hr"
                            action="/hr/userface">
                        <img title="点击修改用户图像"
                             :src="hr.userFace"
                             style="width: 100px;height: 100px;border-radius: 50px"
                             :alt="hr.username">
                    </el-upload>
                </div>
                <div class="userinfo">
                    <ul>
                        <li>
                            <el-tag type="success">用户名</el-tag>
                            {{hr.name}}
                        </li>
                        <li>
                            <el-tag type="success">手机号码</el-tag>
                            {{hr.phone}}
                        </li>
                        <li>
                            <el-tag type="success">电话号码</el-tag>
                            {{hr.telephone}}
                        </li>
                        <li>
                            <el-tag type="success">地址</el-tag>
                            {{hr.address}}
                        </li>
                        <li>
                            <el-tag type="success">用户状态</el-tag>&nbsp;
                            <el-switch
                                    disabled
                                    v-model="hr.enabled">
                            </el-switch>
                        </li>
                        <li style="display: flex;">
                            <el-tag type="success">用户角色</el-tag>
                            <div style="margin-left: 5px">
                                <el-tag style="margin-right: 5px"
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
                                    <el-button icon="el-icon-more" type="text" size="small"
                                               slot="reference"></el-button>
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
                            <el-tag type="info">备注</el-tag>
                            {{hr.remark}}
                        </li>
                    </ul>
                </div>


                <div style="display: flex;justify-content: space-around;margin-top: 20px">
                    <el-button type="primary" @click="showUpdateHrInfoView">修改信息</el-button>
                </div>
            </div>
        </el-card>
        <el-dialog
                title="修改用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <template>
                    <div>
                        <el-tag style="width: 19%;text-align: center;margin-bottom: 10px;">用户昵称</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" v-model="hr2.name"/>
                    </div>
                </template>
                <template>
                    <div>
                        <el-tag style="width: 19%;text-align: center;margin-bottom: 10px;">电话号码</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" v-model="hr2.telephone"/>
                    </div>
                </template>
                <template>
                    <div>
                        <el-tag style="width: 19%;text-align: center;margin-bottom: 10px;">手机号码</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" v-model="hr2.phone"/>
                    </div>
                </template>
                <template>
                    <div>
                        <el-tag style="width: 19%;text-align: center;margin-bottom: 10px;">用户地址</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" v-model="hr2.address"/>
                    </div>
                </template>
                <template>
                    <div>
                        <el-tag style="width: 19%;text-align: center;margin-bottom: 10px;">备注</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" v-model="hr2.remark"/>
                    </div>
                </template>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateHrInfo">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "HrInfo",
        data() {
            return {
                allRoles: [],
                selectedRoles: [],
                hr: null,
                hr2: null,
                dialogVisible: false,
                passwdDialogVisible: false
            }
        },
        mounted() {
            this.initHr();
        },
        methods: {
            initAllRoles() {
                this.getRequest("/system/hr/roles").then(resp => {
                    if (resp) {
                        this.allRoles = resp;
                    }
                })
            },
            showPop(hr) {
                this.initAllRoles();
                let roles = hr.roles;
                this.selectedRoles = [];
                roles.forEach(role => {
                    this.selectedRoles.push(role.id);
                })
            },
            hidePop(hr) {
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
                if (flag) {
                    let url = '/system/hr/roles?hrid=' + hr.id;
                    this.selectedRoles.forEach(sr => {
                        url += "&rids=" + sr;
                    })
                    this.putRequest(url).then(resp => {
                        if (resp) {
                            this.initHr();
                        }
                    })
                }
            },
            onSuccess() {
                this.initHr();
            },
            updateHrInfo() {
                this.putRequest("/hr/info", this.hr2).then(resp => {
                    if (resp) {
                        this.dialogVisible = false;
                        this.initHr();
                    }
                })
            },
            showUpdateHrInfoView() {
                this.dialogVisible = true;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.hrid = this.hr.id;
                        this.putRequest("/hr/pass", this.ruleForm).then(resp => {
                            if (resp) {
                                this.getRequest("/logout");
                                window.sessionStorage.removeItem("user")
                                this.$store.commit('initRoutes', []);
                                this.$router.replace("/");
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            showUpdatePasswdView() {
                this.passwdDialogVisible = true;
            },
            initHr() {
                this.getRequest('/hr/info').then(resp => {
                    if (resp) {
                        this.hr = resp;
                        this.hr2 = Object.assign({}, this.hr);
                        window.sessionStorage.setItem("user", JSON.stringify(resp));
                        this.$store.commit('INIT_CURRENTHR', resp);
                    }
                })
            }
        }
    }
</script>

<style scoped>

    .userinfo ul {
        font-size: 13px;
        list-style: none;
        padding-left: 10px;
    }

    .userinfo ul li {
        margin-bottom: 5px;
    }
</style>