<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initPerTrain"
                      @keydown.enter.native="initPerTrain('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initPerTrain('empName')">搜索</el-button>
        </div>
        <div style="margin-top: 20px">
            <el-table
                    :data="perTrain"
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
                        prop="employeeTrain.trainDate"
                        width="150"
                        label="培训日期">
                </el-table-column>
                <el-table-column
                        prop="employeeTrain.trainContent"
                        width="150"
                        label="培训内容">
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button @click="showEditPerTrainView(scope.row)" icon="el-icon-s-check"
                                   size="small" type="primary">培训</el-button>
                        <el-button @click="delPerTrain(scope.row.id)" icon="el-icon-user"
                                   size="small" type="danger" :disabled="scope.row.employeeTrain.trainDate==null">清除</el-button>
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
                    <el-form :model="empTrain" status-icon  ref="trainForm">
                        <el-form-item label="员工工号" size="mini" >
                            <el-input type="text" v-model=" empTrain.workID" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工姓名" size="mini" >
                            <el-input type="text" v-model=" empTrain.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工性别" size="mini" >
                            <el-input type="text" v-model=" empTrain.gender" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工职位" size="mini" >
                            <el-input type="text" v-model=" empTrain.position.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工部门" size="mini" >
                            <el-input type="text" v-model=" empTrain.department.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="培训日期" size="mini" >
                            <el-date-picker
                                    v-model=" empTrain.employeeTrain.trainDate"
                                    size="mini"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="培训内容" size="mini">
                            <el-input type="text" v-model=" empTrain.employeeTrain.trainContent" style="width: 220px" size="mini"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
                    <el-button type="primary" @click="addEmpTrain" size="mini">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PerTrain",
        data(){
            return{
                empName:'',
                perTrain:[],
                empTrain:{
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
                    employeeTrain:{
                        trainDate:'',
                        trainContent:''
                    }
                },
                total: 0,
                currentPage: 1,
                currentSize: 6,
                dialogVisible: false
            }
        },
        methods:{
            initPerTrain(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/personnel/train/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.perTrain=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/personnel/train/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.perTrain=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initPerTrain();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initPerTrain();
            },
            showEditPerTrainView(data){
                this.dialogVisible=true;
                this.empTrain.id=data.id;
                this.empTrain.workID=data.workID;
                this.empTrain.name=data.name;
                this.empTrain.gender=data.gender;
                this.empTrain.position.name=data.position.name;
                this.empTrain.department.name=data.department.name;
                this.empTrain.employeeTrain.trainDate=data.employeeTrain.trainDate;
                this.empTrain.employeeTrain.trainContent=data.employeeTrain.trainContent;
            },
            addEmpTrain(){
                this.postRequest("/personnel/train/",this.empTrain).then(resp=>{
                    if (resp){
                        this.dialogVisible=false;
                        this.initPerTrain();
                    }
                })
            },
            delPerTrain(eid){
                this.deleteRequest("/personnel/train/?eid="+eid).then(resp=>{
                    if (resp){
                        this.initPerTrain();
                    }
                })
            }
        },
        mounted() {
            this.initPerTrain();
        }
    }
</script>

<style scoped>

</style>