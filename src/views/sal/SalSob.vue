<template>
    <div>
        <div>
            <el-button icon="el-icon-plus"
                       size="small"
                       @click="showAddSalaryViews"
                       type="primary">添加工资账套
            </el-button>
            <el-button icon="el-icon-refresh" type="text" @click="initSalaries"/>
        </div>
        <div style="margin-top: 20px">
            <el-table :data="salaries" border stripe style="width: 100%">
                <el-table-column type="selection" width="40"></el-table-column>
                <el-table-column prop="name" label="账套名称" width="120"></el-table-column>
                <el-table-column prop="basicSalary" label="基本工资" width="90"></el-table-column>
                <el-table-column prop="trafficSalary" label="交通补助" width="90"></el-table-column>
                <el-table-column prop="lunchSalary" label="餐补" width="90"></el-table-column>
                <el-table-column prop="bonus" label="奖金" width="80"></el-table-column>
                <el-table-column prop="allSalary" label="应发工资" width="100"></el-table-column>
                <el-table-column prop="createDate" label="启用时间" width="120"></el-table-column>
                <el-table-column label="养老金" align="center">
                    <el-table-column width="80" prop="pensionPer" label="比率"></el-table-column>
                    <el-table-column width="80" prop="pensionBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="医疗保险" align="center">
                    <el-table-column width="80" prop="medicalPer" label="比率"></el-table-column>
                    <el-table-column width="80" prop="medicalBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="公积金" align="center">
                    <el-table-column width="80" prop="accumulationFundPer" label="比率"></el-table-column>
                    <el-table-column width="80" prop="accumulationFundBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作">
                    <template slot-scope="scope">
                        <el-tooltip content="编辑" placement="bottom" effect="light">
                            <el-button @click="showEditSalaryView(scope.row)" style="padding: 3px" icon="el-icon-edit"
                                       circle type="primary"></el-button>
                        </el-tooltip>
                        <el-tooltip content="删除" placement="bottom" effect="light">
                            <el-button @click="deleteSalary(scope.row)" style="padding: 3px" type="danger"
                                       icon="el-icon-delete" circle></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-dialog
                    :title="dialogTitle"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <div>
                        <el-steps :active="activeItemIndex" finish-status="success">
                            <el-step :key="index"
                                     v-for="(item,index) in salaryItemName"></el-step>
                        </el-steps>
                    </div>
                    <div style="margin-top: 30px;display:flex">
                        <el-tag v-for="(item,index) in salaryItemName"
                                :key="index"
                                style="width: 20%;text-align: center"
                                v-show="activeItemIndex==index">{{item}}
                        </el-tag>
                        <el-input :placeholder="'请输入'+salaryItemName[index]+'...'"
                                  :key="index"
                                  v-show="activeItemIndex==index"
                                  style="width:80%;"
                                  size="small"
                                  v-model="salary[title]"
                                  v-for="(value,title,index) in salary"/>
                    </div>

                </div>
                <template #footer>
                    <span class="dialog-footer">
                      <el-button @click="preStep" size="small">{{activeItemIndex==0?'取消':'上一步'}}</el-button>
                      <el-button type="primary"
                                 @click="nextStep"
                                 size="small">{{activeItemIndex==10?'完成':'下一步'}}</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SalSob",
        data() {
            return {
                dialogVisible: false,
                dialogTitle:'',
                salaries: [],
                activeItemIndex: 0,
                salaryItemName: [
                    '账套名称',
                    '基本工资',
                    '交通补助',
                    '餐补',
                    '奖金',
                    '养老金比率',
                    '养老金基数',
                    '医疗保险比率',
                    '医疗保险基数',
                    '公积金比率',
                    '公积金基数'
                ],
                salary: {
                    name: '',
                    basicSalary:'',
                    trafficSalary: '',
                    lunchSalary: '',
                    bonus: '',
                    pensionPer: '',
                    pensionBase: '',
                    medicalPer: '',
                    medicalBase: '',
                    accumulationFundPer: '',
                    accumulationFundBase: ''
                }
            }
        },
        methods: {
            initSalaries() {
                this.getRequest("/salary/sob/").then(resp => {
                    if (resp) {
                        this.salaries = resp;
                    }
                })
            },
            showAddSalaryViews() {
                this.dialogTitle='添加工资账套';
                this.dialogVisible = true;
                this.salary = {
                    name: '',
                    basicSalary:'',
                    trafficSalary: '',
                    lunchSalary: '',
                    bonus: '',
                    allSalary:'',
                    pensionPer: '',
                    pensionBase: '',
                    medicalPer: '',
                    medicalBase: '',
                    accumulationFundPer: '',
                    accumulationFundBase: ''
                }
            },
            showEditSalaryView(data){
                this.dialogTitle='修改工资账套';
                this.dialogVisible = true;
                this.salary.name = data.name;
                this.salary.basicSalary = data.basicSalary;
                this.salary.trafficSalary = data.trafficSalary;
                this.salary.lunchSalary = data.lunchSalary;
                this.salary.bonus = data.bonus;
                this.salary.pensionPer = data.pensionPer;
                this.salary.pensionBase = data.pensionBase;
                this.salary.medicalPer = data.medicalPer;
                this.salary.medicalBase = data.medicalBase;
                this.salary.accumulationFundPer = data.accumulationFundPer;
                this.salary.accumulationFundBase = data.accumulationFundBase;
                this.salary.id = data.id;
            },
            nextStep() {
                if (this.activeItemIndex == 10) {
                    if (this.salary.id) {
                        this.putRequest("/salary/sob/", this.salary).then(resp=>{
                            if (resp) {
                                this.initSalaries();
                                this.dialogVisible = false;
                                this.activeItemIndex = 0;
                            }
                        })
                    } else {
                        this.postRequest("/salary/sob/", this.salary).then(resp => {
                            if (resp) {
                                this.initSalaries();
                                this.dialogVisible = false;
                                this.activeItemIndex = 0;
                            }
                        });
                    }
                    return
                }
                this.activeItemIndex++;
            },
            preStep() {
                if (this.activeItemIndex == 0) {
                    this.dialogVisible = false;
                    return;
                }
                this.activeItemIndex--;
            },
            deleteSalary(data){
                this.$confirm('此操作将永久删除'+data.name+'账套, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/salary/sob/"+data.id).then(resp=>{
                        if (resp){
                            this.initSalaries();
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
            this.initSalaries();
        }
    }
</script>

<style scoped>

</style>