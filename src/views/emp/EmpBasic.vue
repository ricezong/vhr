<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..." prefix-icon="el-icon-search" size="small" style="width: 300px;margin-right: 5px;"/>
                <el-button icon="el-icon-search" size="small" type="primary">搜索</el-button>
                <el-button icon="el-icon-plus" size="small" type="primary">高级搜索</el-button>
            </div>
            <div>
                <el-button type="success" icon="el-icon-upload2" size="small">导入数据</el-button>
                <el-button type="success" icon="el-icon-download" size="small">导出数据</el-button>
                <el-button type="primary" icon="el-icon-plus" size="small">添加员工</el-button>
            </div>
        </div>
        <div style="margin-top: 20px">
            <el-table
                    :data="emps"
                    border
                    stripe
                    v-loading="loading"
                    style="width: 100%"
                    @selection-change="handleSelectionChange"
                    max-height="350">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="姓名"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="workID"
                        label="工号"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        align="left"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        width="110"
                        align="left"
                        label="出生日期">
                </el-table-column>
                <el-table-column
                        prop="idCard"
                        width="180"
                        align="left"
                        label="身份证号码">
                </el-table-column>
                <el-table-column
                        prop="wedlock"
                        width="80"
                        label="婚姻状况">
                </el-table-column>
                <el-table-column
                        prop="nation.name"
                        width="50"
                        label="民族">
                </el-table-column>
                <el-table-column
                        prop="nativePlace"
                        width="80"
                        label="籍贯">
                </el-table-column>
                <el-table-column
                        prop="politicsStatus.name"
                        label="政治面貌">
                </el-table-column>
                <el-table-column
                        prop="email"
                        width="180"
                        align="left"
                        label="电子邮件">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        width="120"
                        align="left"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="address"
                        width="220"
                        align="left"
                        label="联系地址">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        width="100"
                        align="left"
                        label="所属部门">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        width="100"
                        label="职位">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        width="100"
                        label="职称">
                </el-table-column>
                <el-table-column
                        prop="engageForm"
                        width="100"
                        align="left"
                        label="聘用形式">
                </el-table-column>
                <el-table-column
                        prop="tiptopDegree"
                        width="80"
                        align="left"
                        label="最高学历">
                </el-table-column>
                <el-table-column
                        prop="specialty"
                        width="150"
                        align="left"
                        label="专业">
                </el-table-column>
                <el-table-column
                        prop="school"
                        width="150"
                        align="left"
                        label="毕业院校">
                </el-table-column>
                <el-table-column
                        prop="beginDate"
                        width="100"
                        align="left"
                        label="入职日期">
                </el-table-column>
                <el-table-column
                        prop="conversionTime"
                        width="100"
                        align="left"
                        label="转正日期">
                </el-table-column>
                <el-table-column
                        prop="beginContract"
                        width="110"
                        align="left"
                        label="合同起始日期">
                </el-table-column>
                <el-table-column
                        prop="endContract"
                        width="110"
                        align="left"
                        label="合同截止日期">
                </el-table-column>
                <el-table-column
                        width="90"
                        align="left"
                        label="合同期限">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="120"
                        label="操作">
                    <template slot-scope="scope">
                        <el-tooltip content="高级资料" placement="bottom" effect="light">
                            <el-button style="padding: 3px" icon="el-icon-s-custom" circle></el-button>
                        </el-tooltip>
                        <el-tooltip content="编辑" placement="bottom" effect="light">
                            <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" icon="el-icon-edit" circle type="primary"></el-button>
                        </el-tooltip>
                        <el-tooltip content="删除" placement="bottom" effect="light">
                            <el-button @click="deleteEmp(scope.row)" style="padding: 3px" type="danger" icon="el-icon-delete" circle></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data(){
            return{
                loading:false,
                emps:[],
            }
        },
        methods:{
            initEmps(){
                this.loading=true;
                this.getRequest("/emp/basic/").then(resp=>{
                    this.loading=false;
                    if (resp){
                        this.emps=resp.data;
                    }
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            }
        },
        mounted() {
            this.initEmps();
        }
    }
</script>

<style scoped>

</style>