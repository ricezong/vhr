<template>
    <div>
        <div style="margin-bottom: 20px">
            <el-row>
                <el-col :span="6">
                    部门:
                    <el-popover
                            placement="right"
                            title="请选择部门"
                            trigger="manual"
                            v-model="popVisible">
                        <el-tree
                                default-expand-all
                                :data="allDeps"
                                :props="defaultProps"
                                @node-click="searchViewHandleNodeClick"></el-tree>
                        <div slot="reference"
                             style="width: 220px;display: inline-flex;padding-top:-10px;font-size: 13px;border: 1px solid #dedede;height: 28px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                             @click="showDepView">{{inputDepName}}
                        </div>
                    </el-popover>
                </el-col>
                <el-col :span="6">
                    <span class="demonstration">月份:</span>
                    <el-date-picker
                            size="mini"
                            v-model="workMonth"
                            value-format="yyyy-MM"
                            type="month"
                            placeholder="选择月">
                    </el-date-picker>
                </el-col>
                <el-col :span="6">
                    姓名:
                    <el-input placeholder="姓名"
                              prefix-icon="el-icon-user"
                              size="mini"
                              v-model="empName"
                              clearable
                              style="width: 220px"/>
                </el-col>
                <el-col :span="6">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="initEmpSal">搜索</el-button>
                    <el-button type="info" icon="el-icon-delete" size="mini" @click="resetSearch">清空</el-button>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-table
                    :data="empSal"
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
                        label="基本工资">
                </el-table-column>
                <el-table-column
                        prop="salary.bonus"
                        width="100"
                        label="奖金">
                </el-table-column>
                <el-table-column
                        prop="salary.lunchSalary"
                        width="100"
                        label="餐补">
                </el-table-column>
                <el-table-column
                        prop="salary.trafficSalary"
                        width="100"
                        label="交通补助">
                </el-table-column>
                <el-table-column
                        prop="salary.allSalary"
                        width="100"
                        label="应发工资">
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
    </div>
</template>

<script>
    export default {
        name: "SalTable",
        data(){
            return{
                empSal:[],
                total: 0,
                currentPage: 1,
                currentSize: 6,
                allDeps:[],
                popVisible:false,
                inputDepName:"所属部门",
                defaultProps:{
                    children:'children',
                    label:"name"
                },
                empName:'',
                workMonth:'',
                depID:''
            }
        },
        methods:{
            searchViewHandleNodeClick(data){
                this.inputDepName=data.name;
                this.depID=data.id;
                this.popVisible=!this.popVisible;
            },
            showDepView(){
                this.popVisible=!this.popVisible;
            },
            initEmpSal(){
                let url='/salary/table/?page=' + this.currentPage + '&size=' + this.currentSize;
                if (this.depID){
                    url+='&depID='+this.depID;
                }
                if (this.workMonth){
                    url+='&workMonth='+this.workMonth;
                }
                if (this.empName){
                    url+='&empName='+this.empName;
                }
                this.getRequest(url).then(resp=>{
                    if (resp){
                        this.empSal=resp.data;
                        this.total=resp.total;
                    }
                })
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initEmpSal();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initEmpSal();
            },
            initData(){
                let deps=window.sessionStorage.getItem("deps");
                if (!deps){
                    this.getRequest("/employee/basic/deps").then(resp=>{
                        if(resp){
                            this.allDeps=resp;
                            window.sessionStorage.setItem("deps",JSON.stringify(resp));
                        }
                    })
                }else{
                    this.allDeps=JSON.parse(deps);
                }
            },
            resetSearch(){
                this.depID="";
                this.workMonth="";
                this.empName="";
                this.inputDepName="所属部门";
                this.initEmpSal();
            }
        },
        mounted() {
            this.initEmpSal();
            this.initData();
        }
    }
</script>

<style scoped>

</style>