<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initPerMv"
                      @keydown.enter.native="initPerMv('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initPerMv('empName')">搜索</el-button>
        </div>
        <div style="margin-top: 20px">
            <el-table
                    :data="perMv"
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
                        label="调前职位">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        width="100"
                        label="调前部门">
                </el-table-column>
                <el-table-column
                        prop="position.apname"
                        width="110"
                        label="调后职位">
                </el-table-column>
                <el-table-column
                        prop="department.adname"
                        width="100"
                        label="调后部门">
                </el-table-column>
                <el-table-column
                        prop="employeeRemove.removeDate"
                        width="100"
                        label="调动日期">
                </el-table-column>
                <el-table-column
                        prop="employeeRemove.reason"
                        width="100"
                        label="调动原因">
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button @click="showEditPerMvView(scope.row)" icon="el-icon-user-solid"
                                   size="small" type="primary">调动</el-button>
                        <el-button @click="delPerMv(scope.row.id)" icon="el-icon-user"
                                   size="small" type="danger" :disabled="scope.row.employeeRemove.removeDate==null">清除</el-button>
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
                    <el-form :model="empMv" status-icon  ref="trainForm">
                        <el-form-item label="员工工号" size="mini" >
                            <el-input type="text" v-model="empMv.workID" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工姓名" size="mini" >
                            <el-input type="text" v-model="empMv.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工性别" size="mini" >
                            <el-input type="text" v-model="empMv.gender" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="调前职位" size="mini" >
                            <el-input type="text" v-model="empMv.position.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="调前部门" size="mini" >
                            <el-input type="text" v-model="empMv.department.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="调后职位" size="mini">
                            <el-select v-model="empMv.employeeRemove.afterJobId" placeholder="请选择职位" size="mini" style="width: 220px;">
                                <el-option
                                        v-for="item in allPoses"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="调后部门" size="mini">
                            <el-popover
                                    placement="right"
                                    title="请选择部门"
                                    trigger="manual"
                                    v-model="popVisible">
                                <el-tree
                                        default-expand-all
                                        :data="allDeps"
                                        :props="defaultProps"
                                        @node-click="handleNodeClick"></el-tree>
                                <div slot="reference"
                                     style="width: 220px;display: inline-flex;padding-top:-10px;font-size: 13px;border: 1px solid #dedede;height: 28px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                                     @click="showDepView">{{selectDepName}}
                                </div>
                            </el-popover>
                        </el-form-item>
                        <el-form-item label="调动日期" size="mini" >
                            <el-date-picker
                                    v-model="empMv.employeeRemove.removeDate"
                                    size="mini"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="调动原因" size="mini">
                            <el-input type="text" v-model="empMv.employeeRemove.reason" style="width: 220px" size="mini"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
                    <el-button type="primary" @click="addEmpMv" size="mini">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PerMv",
        data(){
            return{
                empName:'',
                perMv:[],
                empMv:{
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
                    employeeRemove:{
                        afterJobId:'',
                        afterDepId:'',
                        removeDate:'',
                        reason:''
                    }
                },
                allDeps:[],
                allPoses:[],
                total: 0,
                currentPage: 1,
                currentSize: 6,
                dialogVisible: false,
                defaultProps:{
                    children:'children',
                    label:"name"
                },
                popVisible:false,
                selectDepName:"请选择部门"
            }
        },
        methods:{
            initPerMv(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/personnel/remove/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.perMv=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/personnel/remove/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.perMv=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
            },
            initData(){
                let deps=window.sessionStorage.getItem("deps");
                let poses=window.sessionStorage.getItem("poses");
                if (!deps){
                    this.getRequest("/personnel/remove/deps/").then(resp=>{
                        if(resp){
                            this.allDeps=resp;
                            window.sessionStorage.setItem("deps",JSON.stringify(resp));
                        }
                    })
                }else{
                    this.allDeps=JSON.parse(deps);
                }
                if (!poses){
                    this.getRequest("/personnel/remove/poses/").then(resp=>{
                        if(resp){
                            this.allPoses=resp;
                            window.sessionStorage.setItem("poses",JSON.stringify(resp));
                        }
                    })
                }else{
                    this.allPoses=JSON.parse(poses);
                }

            },
            sizeChange(size) {
                this.currentSize = size;
                this.initPerMv();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initPerMv();
            },
            showEditPerMvView(data){
                this.dialogVisible=true;
                this.empMv.id=data.id;
                this.empMv.workID=data.workID;
                this.empMv.name=data.name;
                this.empMv.gender=data.gender;
                this.empMv.position.name=data.position.name;
                this.empMv.department.name=data.department.name;
                this.selectDepName='请选择部门';
                this.empMv.employeeRemove.afterJobId=data.employeeRemove.afterJobId;
                this.empMv.employeeRemove.afterDepId=data.employeeRemove.afterDepId;
                this.empMv.employeeRemove.removeDate=data.employeeRemove.removeDate;
                this.empMv.employeeRemove.reason=data.employeeRemove.reason;
            },
            addEmpMv(){
                this.postRequest("/personnel/remove/",this.empMv).then(resp=>{
                    if (resp){
                        this.dialogVisible=false;
                        this.initPerMv();
                    }
                })
            },
            delPerMv(eid){
                this.deleteRequest("/personnel/remove/?eid="+eid).then(resp=>{
                    if (resp){
                        this.initPerMv();
                    }
                })
            },
            handleNodeClick(data){
                this.selectDepName=data.name;
                this.empMv.employeeRemove.afterDepId=data.id;
                this.popVisible=!this.popVisible;
            },
            showDepView(){
                this.popVisible=!this.popVisible;
            },

        },
        mounted() {
            this.initPerMv();
            this.initData();
        }
    }
</script>

<style scoped>

</style>