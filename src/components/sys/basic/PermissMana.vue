<template>
    <div>
        <div style="margin-bottom: 10px">
            <el-input placeholder="请输入角色英文名" v-model="role.name" size="small" style="width: 300px;margin-right: 5px;">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input placeholder="请输入角色中文名" v-model="role.nameZh" size="small" @keydown.enter.native="doAddRole" style="width: 300px;margin-right: 5px;"/>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="doAddRole">添加角色</el-button>
        </div>
        <div>
            <el-collapse v-model="activeName" accordion style="width: 60%" @change="change">
                <el-collapse-item :title="role.nameZh" :name="role.id" v-for="(role,index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: red"
                                       icon="el-icon-delete"
                                       @click="deleteRole(role)"
                                       type="text">
                            </el-button>
                        </div>
                        <div>
                            <el-tree
                                    :data="allMenus"
                                    :props="defaultProps"
                                    show-checkbox
                                    node-key="id"
                                    ref="tree"
                                    :key="index"
                                    :default-checked-keys="selectedMenus"
                                    accordion>
                            </el-tree>
                            <div style="display: flex;justify-content: flex-end;">
                                <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                                <el-button size="mini" type="primary" @click="doUpdate(role.id,index)">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PermissMana",
        data(){
            return{
                role:{
                    name:'',
                    nameZh:''
                },
                roles:[],
                activeName:-1,
                allMenus:[],
                selectedMenus:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        methods:{
            initRoles(){
                this.getRequest("/system/basic/permiss/").then(resp=>{
                    if (resp){
                        this.roles=resp;
                    }
                })
            },
            initAllMenus(){
                this.getRequest("/system/basic/permiss/menus").then(resp=>{
                    if (resp){
                        this.allMenus=resp;
                    }
                })
            },
            initSelectedMenus(rid){
                this.getRequest("/system/basic/permiss/mids/"+rid).then(resp=>{
                    if (resp){
                        this.selectedMenus=resp;
                    }
                })
            },
            change(rid){
                if(rid){
                    this.initAllMenus();
                    this.initSelectedMenus(rid);
                }
            },
            doUpdate(rid,index){
                let tree=this.$refs.tree[index];
                let selectedKeys=tree.getCheckedKeys(true);
                console.log(selectedKeys)
                let url="/system/basic/permiss/?rid="+rid;
                selectedKeys.forEach(key=>{
                    url+="&mids="+key;
                })
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.activeName=-1;
                    }
                })
            },
            cancelUpdate(){
                this.activeName=-1;
            },
            doAddRole(){
                if (this.role.name&&this.role.nameZh){
                    this.postRequest("/system/basic/permiss/role",this.role).then(resp=>{
                        if (resp){
                            this.initRoles();
                            this.role.name='';
                            this.role.nameZh='';
                        }
                    })
                }else{
                    this.$message({
                        showClose: true,
                        message: '角色名不可以为空',
                        type: 'warning'
                    });
                }
            },
            deleteRole(role){
                this.$confirm('此操作将永久删除'+role.nameZh+'角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/permiss/role/"+role.id).then(resp=>{
                        if (resp){
                            this.initRoles();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        mounted() {
            this.initRoles();
        }
    }
</script>

<style scoped>


</style>