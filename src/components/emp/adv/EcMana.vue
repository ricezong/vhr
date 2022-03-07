<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initEc"
                      @keydown.enter.native="initEc('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initEc('empName')">搜索</el-button>
            <el-button icon="el-icon-refresh" type="text" @click="initEc"/>
        </div>
        <div>
            <el-table
                    ref="filterTable"
                    :data="ecs"
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
                        prop="employeeEc.ecDate"
                        width="100"
                        label="奖惩日期">
                </el-table-column>
                <el-table-column
                        prop="employeeEc.ecReason"
                        width="100"
                        label="奖惩原因">
                </el-table-column>
                <el-table-column
                        prop="employeeEc.ecPoint"
                        width="100"
                        align="center"
                        label="奖惩分数">
                </el-table-column>
                <el-table-column
                        prop="ecType"
                        label="标签"
                        width="100"
                        :filters="[{ text: '奖励', value: 0 }, { text: '惩罚', value: 1 }]"
                        :filter-method="filterTag"
                        filter-placement="right">
                    <template slot-scope="scope">
                        <el-tag
                                :type="scope.row.employeeEc.ecType === 0 ? 'primary' : 'danger'"
                                disable-transitions>{{scope.row.employeeEc.ecType==0?'奖':'罚'}}</el-tag>
                    </template>
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
        name: "EcMana",
        data() {
            return {
                ecs: [],
                empName:'',
                total: 0,
                currentPage: 1,
                currentSize: 6,
                multipleSelection:[]
            }
        },
        methods: {
            filterTag(value, row) {
                return row.employeeEc.ecType === value;
            },
            initEc(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/emp/adv/ec/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.ecs=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/emp/adv/ec/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.ecs=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
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
                    this.deleteRequest("/emp/adv/ec/"+ids).then(resp=>{
                        if (resp){
                            this.initEc();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initEc();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initEc();
            }
        },
        mounted(){
            this.initEc();
        }
    }
</script>

<style scoped>

</style>