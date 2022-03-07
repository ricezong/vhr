<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initAdJust"
                      @keydown.enter.native="initAdJust('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initAdJust('empName')">搜索</el-button>
            <el-button icon="el-icon-refresh" type="text" @click="initAdJust"/>
        </div>
        <div>
            <el-table
                    ref="filterTable"
                    :data="adJusts"
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="workID"
                        label="工号"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="姓名"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        width="100"
                        label="职位">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        width="100"
                        label="部门">
                </el-table-column>
                <el-table-column
                        prop="salary.basicSalary"
                        width="100"
                        label="调前薪资">
                </el-table-column>
                <el-table-column
                        prop="adjustSalary.afterSalary"
                        width="100"
                        label="调后薪资">
                </el-table-column>
                <el-table-column
                        prop="adjustSalary.asDate"
                        width="100"
                        label="调薪日期">
                </el-table-column>
                <el-table-column
                        prop="adjustSalary.reason"
                        width="150"
                        label="调薪原因">
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-start;margin-top: 10px">
                <el-button icon="el-icon-delete"
                           size="small"
                           type="danger"
                           :disabled="multipleSelection.length==0"
                           @click="deleteMany">批量删除</el-button>
                <el-pagination
                        background
                        style="margin-left: 400px;"
                        @size-change="sizeChange"
                        @current-change="currentChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AdJustMana",
        data(){
            return{
                adJusts:[],
                empName:'',
                total: 0,
                currentPage: 1,
                currentSize: 6,
                multipleSelection:[]
            }
        },
        methods:{
            initAdJust(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/emp/adv/adjust/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.adJusts=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/emp/adv/adjust/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.adJusts=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initAdJust();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initAdJust();
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
                    this.deleteRequest("/emp/adv/adjust/"+ids).then(resp=>{
                        if (resp){
                            this.initAdJust();
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
        mounted(){
            this.initAdJust();
        }
    }
</script>

<style scoped>

</style>