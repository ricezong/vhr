<template>
    <div>
        <div>
            <el-input size="small"
                      v-model="jl.name"
                      style="width: 260px;margin-right: 5px"
                      prefix-icon="el-icon-plus"
                      placeholder="添加职称..."/>
            <el-select v-model="jl.titleLevel"
                       placeholder="职称等级"
                       style="margin-right: 5px"
                       size="small">
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>
            <el-button icon="el-icon-plus" type="primary" size="small" @click="addJobLevel">添加</el-button>
        </div>
        <div>
            <el-table
                    :data="jls"
                    border
                    stripe
                    size="small"
                    @selection-change="handleSelectionChange"
                    max-height="385"
                    style="width: 80%;margin: 10px 0;">
                <el-table-column
                        type="selection"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="150">
                </el-table-column>
                <el-table-column label="是否启用" width="80">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag type="info" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEditView(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button size="small" type="danger" :disabled="multipleSelection.length==0" @click="deleteMany">批量删除</el-button>
        </div>
        <div>
            <el-dialog
                    title="修改职称"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">职称名</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" size="small" v-model="updateJl.name"/>
                    </template>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">职称级别</el-tag>
                        <el-select v-model="updateJl.titleLevel"
                                   placeholder="职称等级"
                                   style="width: 80%;margin-left: 1%"
                                   size="small">
                            <el-option
                                    v-for="item in titleLevels"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                    </template>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">是否启用</el-tag>
                        <el-switch
                                style="width: 80%;margin-left: 1%"
                                v-model="updateJl.enabled"
                                active-color="#13ce66"
                                inactive-color="#ff4949">
                        </el-switch>
                    </template>
                </div>
                <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "JobLevelMana",
        data(){
            return{
                multipleSelection:[],
                dialogVisible:false,
                updateJl:{
                    name:'',
                    titleLevel:'',
                    enabled:false
                },
                jl:{
                    name:'',
                    titleLevel:''
                },
                jls:[],
                titleLevels:[
                    '正高级',
                    '副高级',
                    '中级',
                    '初级',
                    '员级'
                ]
            }
        },
        methods:{
            initJls(){
                this.getRequest("/system/basic/joblevel/").then(resp=>{
                    if (resp){
                        this.jls=resp;
                    }
                })
            },
            addJobLevel(){
                if (this.jl.name&&this.jl.titleLevel){
                    this.postRequest("/system/basic/joblevel/",this.jl).then(resp=>{
                        if (resp){
                            this.initJls();
                            this.jl={
                                name:'',
                                titleLevel:''
                            }
                        }
                    })
                }else{
                    this.$message({
                        showClose: true,
                        message: '添加字段不可以为空',
                        type: 'warning'
                    });
                }
            },
            handleDelete(data){
                this.$confirm('此操作将永久删除'+data.name+'职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/joblevel/"+data.id).then(resp=>{
                        if (resp){
                            this.initJls();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            showEditView(data){
                Object.assign(this.updateJl,data);
                this.dialogVisible=true;
            },
            doUpdate(){
                this.putRequest("/system/basic/joblevel/",this.updateJl).then(resp=>{
                    if (resp){
                        this.initJls();
                        this.dialogVisible=false;
                    }
                })
            },
            handleSelectionChange(val){
                this.multipleSelection=val;
            },
            deleteMany(){
                this.$confirm('此操作将永久删除'+this.multipleSelection.length+'条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&';
                    })
                    this.deleteRequest("/system/basic/joblevel/"+ids).then(resp=>{
                        if (resp){
                            this.initJls();
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
            this.initJls();
        }
    }
</script>

<style scoped>

</style>