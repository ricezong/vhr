<template>
    <div>
        <div>
            <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                      prefix-icon="el-icon-search"
                      size="small"
                      v-model="empName"
                      clearable
                      @clear="initPerEc"
                      @keydown.enter.native="initPerEc('empName')"
                      style="width: 350px;margin-right: 5px;"/>
            <el-button icon="el-icon-search" size="small" type="primary" @click="initPerEc('empName')">搜索</el-button>
        </div>
        <div style="margin-top: 20px">
            <el-table
                    :data="perEc"
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
                        prop="employeeEc.ecDate"
                        width="100"
                        label="奖惩日期">
                </el-table-column>
                <el-table-column
                        prop="employeeEc.ecType"
                        width="100"
                        align="center"
                        label="奖惩类别">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.employeeEc.ecType == 0 ? 'primary' : 'danger'"
                                v-show="scope.row.employeeEc.ecType!=null">{{scope.row.employeeEc.ecType==0?'奖':'罚'}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="employeeEc.ecReason"
                        width="100"
                        label="奖惩原因">
                </el-table-column>
                <el-table-column
                        prop="testScore"
                        width="100"
                        align="center"
                        label="考评分">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.testScore}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button @click="showEditPerEcView(scope.row)" icon="el-icon-user-solid"
                                   size="small" type="primary">奖惩</el-button>
                        <el-button @click="delPerEc(scope.row.id)" icon="el-icon-user"
                                   size="small" type="danger" :disabled="scope.row.testScore==0">清除</el-button>
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
                    title="奖惩"
                    :visible.sync="dialogVisible"
                    width="23%">
                <div>
                    <el-form :model="empEc" status-icon  ref="ecForm">
                        <el-form-item label="员工工号" size="mini">
                            <el-input type="text" v-model="empEc.workID" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工姓名" size="mini">
                            <el-input type="text" v-model="empEc.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工性别" size="mini">
                            <el-input type="text" v-model="empEc.gender" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工职位" size="mini">
                            <el-input type="text" v-model="empEc.position.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="员工部门" size="mini">
                            <el-input type="text" v-model="empEc.department.name" style="width: 220px" size="mini" readonly disabled></el-input>
                        </el-form-item>
                        <el-form-item label="奖惩原因" size="mini">
                            <el-input type="text" v-model="empEc.employeeEc.ecReason" style="width: 220px" size="mini"></el-input>
                        </el-form-item>
                        <el-form-item label="奖惩日期" size="mini" prop="ecDate">
                            <el-date-picker
                                    v-model="empEc.employeeEc.ecDate"
                                    size="mini"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="奖惩分数" size="mini" prop="ecPoint">
                            <el-input-number v-model="empEc.employeeEc.ecPoint"
                                             style="width: 220px"
                                             :min="1"
                                             :max="10"
                                             size="mini"/>
                        </el-form-item>
                        <el-form-item label="奖惩类别" size="mini" prop="ecType">
                            <el-radio-group v-model="empEc.employeeEc.ecType" style="width: 200px">
                                <el-radio label="0">奖励</el-radio>
                                <el-radio label="1">惩罚</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
                    <el-button type="primary" @click="addEmpEc" size="mini">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PerEc",
        data(){
            return{
                empName:'',
                perEc:[],
                empEc:{
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
                    employeeEc:{
                        ecDate:'',
                        ecType:'',
                        ecReason:'',
                        ecPoint:''
                    }
                },
                total: 0,
                currentPage: 1,
                currentSize: 6,
                dialogVisible: false,
            }
        },
        methods:{
            initPerEc(keyWord){
                if (keyWord&&keyWord=='empName'){
                    this.getRequest("/personnel/ec/?page=" + this.currentPage + '&size=' + this.currentSize+'&empName='+this.empName).then(resp=>{
                        if (resp){
                            this.perEc=resp.data;
                            this.total=resp.total;
                        }
                    })
                }else{
                    this.getRequest("/personnel/ec/?page=" + this.currentPage + '&size=' + this.currentSize).then(resp=>{
                        if (resp){
                            this.perEc=resp.data;
                            this.total=resp.total;
                        }
                    })
                }
            },
            addEmpEc(){
                this.postRequest("/personnel/ec/",this.empEc).then(resp=>{
                    if (resp){
                        this.dialogVisible=false;
                        this.initPerEc();
                    }
                })
            },
            delPerEc(eid){
              this.deleteRequest("/personnel/ec/?eid="+eid).then(resp=>{
                  if (resp){
                      this.initPerEc();
                  }
              })
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initPerEc();
            },
            currentChange(page) {
                this.currentPage = page;
                this.initPerEc();
            },
            showEditPerEcView(data){
                this.dialogVisible=true;
                this.empEc.id=data.id;
                this.empEc.workID=data.workID;
                this.empEc.name=data.name;
                this.empEc.gender=data.gender;
                this.empEc.position.name=data.position.name;
                this.empEc.department.name=data.department.name;
                this.empEc.employeeEc={
                        ecDate:'',
                        ecType:'',
                        ecReason:'',
                        ecPoint:''
                }
            }
        },
        mounted() {
            this.initPerEc();
        }
    }
</script>

<style scoped>

</style>