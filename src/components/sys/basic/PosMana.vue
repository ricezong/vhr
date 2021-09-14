<template>
    <div>
        <div>
            <el-input
                    size="small"
                    class="addPosInput"
                    placeholder="添加职位..."
                    prefix-icon="el-icon-plus"
                    @keydown.enter.native="addPosition"
                    v-model="pos.name">
            </el-input>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
        </div>
        <div>
            <el-table
                    :data="positions"
                    border
                    size="small"
                    stripe
                    @selection-change="handleSelectionChange"
                    class="posManaMain">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
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
                                @click="showEditView(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button size="small" type="danger" :disabled="multipleSelection.length==0" @click="deleteMany">批量删除</el-button>
        </div>
        <div>
            <el-dialog
                    title="修改职位"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">职位名称</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" size="small" v-model="updatePos.name"/>
                    </template>
                    <template>
                        <template>
                            <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">是否启用</el-tag>
                            <el-switch
                                    style="width: 80%;margin-left: 1%"
                                    v-model="updatePos.enabled"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </template>
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
        name: "PosMana",
        data(){
            return{
                pos:{
                    name:''
                },
                updatePos:{
                    name:'',
                    enabled:false
                },
                positions: [],
                multipleSelection:[],
                dialogVisible:false
            }
        },
        methods:{
            initPositions(){
                this.getRequest("/system/basic/pos/").then(resp=>{
                    if (resp){
                        this.positions=resp;
                        this.pos.name='';
                    }
                })
            },
            addPosition(){
                if (this.pos.name){
                    this.postRequest("/system/basic/pos/",this.pos).then(resp=>{
                        if (resp){
                            this.initPositions();
                        }
                    })
                }else {
                    this.$message({
                        showClose: true,
                        message: '职位名称不可以为空',
                        type: 'warning'
                    });
                }
            },
            showEditView(index,data){
                Object.assign(this.updatePos,data);
                this.dialogVisible=true;
            },
            doUpdate(){
                this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
                    if (resp){
                        this.initPositions();
                        this.updatePos.name='';
                        this.dialogVisible=false;
                    }
                })
            },
            handleDelete(index,data){
                this.$confirm('此操作将永久删除'+data.name+'职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/"+data.id).then(resp=>{
                        if (resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
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
                    this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
                        if (resp){
                            this.initPositions();
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
            this.initPositions();
        }
    }
</script>

<style scoped>
    .addPosInput{
        width: 300px;
        margin-right: 3px;
    }
    .posManaMain{
        margin: 10px 0;
        width: 60%;
    }
</style>