<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initPerSalary"
                      @keydown.enter.native="initPerSalary('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initPerSalary('empName')">搜索</el-button>
        </div>
        <div style="margin-top: 20px">
            <el-table
                    :data="perSalary"
                    border
                    stripe
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
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button plain @click="showEditPerSalaryView(scope.row)" icon="el-icon-bank-card"
                                   size="small" type="primary">调薪</el-button>

                        <el-button @click="delPerSalary(scope.row.id)" icon="el-icon-bank-card"
                                   size="small" type="danger" :disabled="scope.row.adjustSalary.asDate==null">清除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end;margin-top: 10px">
                <el-pagination
                        background
                        @size-change="sizeChange"
                        @current-change="currentChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <div>
            <el-dialog
                    title="培训"
                    :visible.sync="dialogVisible"
                    width="23%">
                <div>
                    <el-form :model="empSalary" status-icon  ref="trainForm">
                        <el-form-item label="员工工号" size="mini" >
                            <el-input type="text" v-model="empSalary.workID" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工姓名" size="mini" >
                            <el-input type="text" v-model="empSalary.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工性别" size="mini" >
                            <el-input type="text" v-model="empSalary.gender" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工职位" size="mini" >
                            <el-input type="text" v-model="empSalary.position.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工部门" size="mini" >
                            <el-input type="text" v-model="empSalary.department.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="调前薪资" size="mini" >
                            <el-input type="text" v-model="empSalary.salary.basicSalary" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="调后薪资" size="mini" >
                            <el-input type="text" v-model="empSalary.adjustSalary.afterSalary" style="width: 220px" size="mini"></el-input>
                        </el-form-item>
                        <el-form-item label="调薪日期" size="mini" >
                            <el-date-picker
                                    v-model="empSalary.adjustSalary.asDate"
                                    size="mini"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="调薪原因" size="mini" >
                            <el-input type="text" v-model="empSalary.adjustSalary.reason" style="width: 220px" size="mini"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
                    <el-button type="primary" @click="addEmpSalary" size="mini">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PerSalary",
        data(){
            return{
                empName:'',
                perSalary:[],
                empSalary:{
                    id:'',
                    workID:'',
                    name:'',
                    gender:'',
                    position:{
                        name:''
                    },
                    department:{
                        name:''
                    },
                    salary:{
                        basicSalary:''
                    },
                    adjustSalary:{
                        beforeSalary:'',
                        afterSalary:'',
                        asDate:'',
                        reason:''
                    }
                },
                total: 0,
                currentPage: 1,
                currentSize: 6,
                dialogVisible: false,
            }
        },
        methods:{
            initPerSalary(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/personnel/salary/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.perSalary=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/personnel/salary/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.perSalary=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initPerSalary();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initPerSalary();
            },
            showEditPerSalaryView(data){
                if (data.salary==null){
                    const h = this.$createElement;
                    this.$notify({
                        title: '消息提示',
                        message: h('i', { style: 'color: teal'}, '请先为该员工设置工资账套！')
                    });
                    this.dialogVisible=false;
                }else {
                    this.dialogVisible=true;
                    this. empSalary.id=data.id;
                    this. empSalary.workID=data.workID;
                    this. empSalary.name=data.name;
                    this. empSalary.gender=data.gender;
                    this. empSalary.position.name=data.position.name;
                    this. empSalary.department.name=data.department.name;
                    this. empSalary.salary.basicSalary=data.salary.basicSalary;
                    this. empSalary.adjustSalary.beforeSalary=data.salary.basicSalary;
                    this. empSalary.adjustSalary.afterSalary=data.adjustSalary.afterSalary;
                    this. empSalary.adjustSalary.asDate=data.adjustSalary.asDate;
                    this. empSalary.adjustSalary.reason=data.adjustSalary.reason;
                }
            },
            addEmpSalary(){
                this.postRequest("/personnel/salary/",this.empSalary).then(resp=>{
                    if (resp){
                        this.dialogVisible=false;
                        this.initPerSalary();
                    }
                })
            },
            delPerSalary(eid){
                this.deleteRequest("/personnel/salary/?eid="+eid).then(resp=>{
                    if (resp){
                        this.initPerSalary();
                    }
                })
            }
        },
        mounted() {
            this.initPerSalary();
        }
    }
</script>

<style scoped>

</style>