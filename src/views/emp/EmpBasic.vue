<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工名进行搜索,可以直击回车搜索..."
                              prefix-icon="el-icon-search"
                              size="small"
                              v-model="empName"
                              :disabled="showAdvanceSearchView"
                              clearable
                              @clear="initEmps"
                              @keydown.enter.native="initEmps"
                              style="width: 350px;margin-right: 5px;"/>
                    <el-button icon="el-icon-search" size="small" type="primary" :disabled="showAdvanceSearchView" @click="initEmps">搜索</el-button>
                    <el-button :icon="showAdvanceSearchView?'el-icon-arrow-up':'el-icon-arrow-down'" size="small" type="primary" @click="showAdvanceSearchView=!showAdvanceSearchView">高级搜索</el-button>
                </div>
                <div>
                    <el-upload
                            style="display: inline-flex;margin-right: 10px;"
                            :disabled="importDataDisabled"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            action="/employee/basic/import"
                            :show-file-list="false">
                        <el-button :disabled="importDataDisabled" type="success" icon="el-icon-upload2" size="small" :icon="importDataBtnIcon">{{importDataBtnText}}</el-button>
                    </el-upload>
                    <el-button type="success" icon="el-icon-download" size="small" @click="exportData">导出数据</el-button>
                    <el-button type="primary" icon="el-icon-plus" size="small" @click="showAddEmpView">添加员工</el-button>
                </div>
            </div>
            <transition name="fade">
                <div v-show="showAdvanceSearchView" style="border: 1px solid #EBEEF5;border-radius: 5px;box-sizing: border-box;padding: 10px;margin: 10px 0px">
                <el-row style="margin-bottom: 20px;">
                    <el-col :span="6">
                        政治面貌:
                        <el-select v-model="advanceSearchValue.politicId" placeholder="政治面貌" style="width: 200px" size="mini">
                            <el-option
                                    v-for="item in politicsstatus"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6">
                        民族:
                        <el-select v-model="advanceSearchValue.nationId" placeholder="民族" size="mini" style="width: 200px;">
                            <el-option
                                    v-for="item in nations"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6">
                        职位:
                        <el-select v-model="advanceSearchValue.posId" placeholder="职位" size="mini" style="width: 200px;">
                            <el-option
                                    v-for="item in positions"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6">
                        职称:
                        <el-select v-model="advanceSearchValue.jobLevelId" placeholder="职称" size="mini" style="width: 200px;">
                            <el-option
                                    v-for="item in joblevels"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">
                        聘用形式:
                        <el-radio-group v-model="advanceSearchValue.engageForm" size="mini">
                            <el-radio label="劳动合同">劳动合同</el-radio>
                            <el-radio label="劳务合同">劳务合同</el-radio>
                        </el-radio-group>
                    </el-col>
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
                                 style="width: 200px;display: inline-flex;padding-top:-10px;font-size: 13px;border: 1px solid #dedede;height: 28px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                                 @click="showDepView">{{inputDepName}}
                            </div>
                        </el-popover>
                    </el-col>
                    <el-col :span="6">
                        入职:
                        <el-date-picker
                                style="width: 200px"
                                size="mini"
                                v-model="advanceSearchValue.dateScope"
                                value-format="yyyy-MM-dd "
                                type="daterange"
                                range-separator="-"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-col>
                    <el-col :span="5" :offset="1">
                        <el-button size="mini" @click="resetAdvSearch">取消</el-button>
                        <el-button size="mini" type="primary" @click="initEmps('advanced')">搜索</el-button>
                    </el-col>
                </el-row>
            </div>
            </transition>
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
                        <el-tooltip :content="scope.row.name" placement="bottom" effect="light">
                            <el-button style="padding: 3px" icon="el-icon-s-custom" circle></el-button>
                        </el-tooltip>
                        <el-tooltip content="编辑" placement="bottom" effect="light">
                            <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" icon="el-icon-edit"
                                       circle type="primary"></el-button>
                        </el-tooltip>
                        <el-tooltip content="删除" placement="bottom" effect="light">
                            <el-button @click="deleteEmp(scope.row)" style="padding: 3px" type="danger"
                                       icon="el-icon-delete" circle></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="margin-top: 20px;display: flex;justify-content: flex-end;">
            <el-pagination
                    background
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog
                    :title="title"
                    :visible.sync="dialogVisible"
                    width="60%">
                <div>
                    <el-form :model="emp" :rules="rules" ref="empForm">
                        <el-collapse v-model="activeName" accordion ref="collapse">
                            <el-collapse-item title="基本信息" name="1">
                                <div>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="姓名:" prop="name" size="mini">
                                                <el-input prefix-icon="el-icon-edit" placeholder="请输入员工姓名" size="mini"  v-model="emp.name" style="width: 200px"/>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="政治面貌:" prop="politicId" size="mini">
                                                <el-select v-model="emp.politicId" placeholder="政治面貌" style="width: 200px" size="mini">
                                                    <el-option
                                                            v-for="item in politicsstatus"
                                                            :key="item.id"
                                                            :label="item.name"
                                                            :value="item.id">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="出生日期:" prop="birthday" size="mini">
                                                <el-date-picker
                                                        v-model="emp.birthday"
                                                        size="mini"
                                                        type="date"
                                                        value-format="yyyy-MM-dd"
                                                        style="width: 200px"
                                                        placeholder="出生日期">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="性别:" prop="gender" size="mini">
                                                <el-radio-group v-model="emp.gender" style="width: 200px">
                                                    <el-radio label="男">男</el-radio>
                                                    <el-radio label="女">女</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="电子邮箱:" prop="email" size="mini">
                                                <el-input size="mini"
                                                          style="width: 200px"
                                                          prefix-icon="el-icon-message"
                                                          v-model="emp.email"
                                                          placeholder="请输入电子邮箱"/>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="身份证号:" prop="idCard" size="mini">
                                                <el-input size="mini" prefix-icon="el-icon-edit"
                                                          v-model="emp.idCard" placeholder="请输入身份证号码" style="width: 200px"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="民族:" prop="nationId" size="mini">
                                                <el-select v-model="emp.nationId" placeholder="民族" size="mini" style="width: 200px;">
                                                    <el-option
                                                            v-for="item in nations"
                                                            :key="item.id"
                                                            :label="item.name"
                                                            :value="item.id">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="电话号码:" prop="phone" size="mini">
                                                <el-input size="mini"
                                                          style="width: 200px"
                                                          prefix-icon="el-icon-phone"
                                                          v-model="emp.phone"
                                                          placeholder="电话号码"/>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="联系地址:" prop="address" size="mini">
                                                <el-input size="mini"
                                                          style="width: 200px"
                                                          prefix-icon="el-icon-edit"
                                                          v-model="emp.address"
                                                          placeholder="请输入联系地址"/>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="学历:" prop="tiptopDegree" size="mini">
                                                <el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini"
                                                           style="width: 200px;">
                                                    <el-option
                                                            v-for="item in tiptopDegrees"
                                                            :key="item"
                                                            :label="item"
                                                            :value="item">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="毕业院校:" prop="school" size="mini">
                                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                                                          v-model="emp.school" placeholder="毕业院校名称"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="专业名称:" prop="specialty" size="mini">
                                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                                                          v-model="emp.specialty" placeholder="请输入专业名称"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="籍贯:" prop="nativePlace" size="mini">
                                                <el-input size="mini"
                                                          style="width: 200px"
                                                          prefix-icon="el-icon-edit"
                                                          v-model="emp.nativePlace"
                                                          placeholder="请输入籍贯"/>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="9">
                                            <el-form-item label="婚姻状况:" prop="wedlock" size="mini">
                                                <el-radio-group v-model="emp.wedlock" size="mini">
                                                    <el-radio label="已婚">已婚</el-radio>
                                                    <el-radio label="未婚">未婚</el-radio>
                                                    <el-radio label="离异">离异</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-collapse-item>
                            <el-collapse-item title="入职信息" name="2">
                                <div>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="工号:" prop="workID" size="mini">
                                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                                                          v-model="emp.workID" placeholder="工号" disabled></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="入职日期:" prop="beginDate" size="mini">
                                                <el-date-picker
                                                        v-model="emp.beginDate"
                                                        size="mini"
                                                        type="date"
                                                        value-format="yyyy-MM-dd"
                                                        style="width: 200px;"
                                                        placeholder="入职日期">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="转正日期:" prop="conversionTime" size="mini">
                                                <el-date-picker
                                                        v-model="emp.conversionTime"
                                                        size="mini"
                                                        type="date"
                                                        value-format="yyyy-MM-dd"
                                                        style="width: 200px;"
                                                        placeholder="转正日期">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>

                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="职位:" prop="posId" size="mini">
                                                <el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 200px;">
                                                    <el-option
                                                            v-for="item in positions"
                                                            :key="item.id"
                                                            :label="item.name"
                                                            :value="item.id">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="所属部门:" prop="departmentId" size="mini">
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
                                                         style="width: 200px;display: inline-flex;padding-top:-10px;font-size: 13px;border: 1px solid #dedede;height: 28px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                                                         @click="showDepView">{{inputDepName}}
                                                    </div>
                                                </el-popover>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="聘用形式:" prop="engageForm" size="mini">
                                                <el-radio-group v-model="emp.engageForm" size="mini">
                                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="8">
                                            <el-form-item label="职称:" prop="jobLevelId" size="mini">
                                                <el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 200px;">
                                                    <el-option
                                                            v-for="item in joblevels"
                                                            :key="item.id"
                                                            :label="item.name"
                                                            :value="item.id">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="合同生效:" prop="beginContract" size="mini">
                                                <el-date-picker
                                                        v-model="emp.beginContract"
                                                        size="mini"
                                                        type="date"
                                                        value-format="yyyy-MM-dd"
                                                        style="width: 200px;"
                                                        placeholder="合同起始日期">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item label="合同终止:" prop="endContract" size="mini">
                                                <el-date-picker
                                                        v-model="emp.endContract"
                                                        size="mini"
                                                        type="date"
                                                        value-format="yyyy-MM-dd"
                                                        style="width: 200px;"
                                                        placeholder="合同终止日期">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-collapse-item>
                        </el-collapse>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doAddEmp">确 定</el-button>
                </span>
            </el-dialog>

        </div>
    </div>
</template>

<script>
    import {Message} from "element-ui";

    export default {
        name: "EmpBasic",
        data() {
            return {
                advanceSearchValue:{
                    politicId:null,
                    nationId:null,
                    jobLevelId:null,
                    posId:null,
                    engageForm:"",
                    departmentId:null,
                    dateScope:null
                },
                importDataBtnText:"导入数据",
                importDataBtnIcon:"el-icon-upload2",
                importDataDisabled:false,
                showAdvanceSearchView:false,
                title:"",
                loading: false,
                popVisible:false,
                inputDepName:"所属部门",
                dialogVisible: false,
                activeName: [],
                allDeps:[],
                emps: [],
                total: 0,
                page: 1,
                size: 10,
                empName: '',
                nations:[],
                joblevels:[],
                politicsstatus:[],
                positions:[],
                tiptopDegrees:['小学','初中','高中','专科','本科','硕士','博士','院士','其他'],
                emp: {
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: "",
                    nativePlace: "",
                    politicId: "",
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: "",
                    jobLevelId: "",
                    posId: "",
                    engageForm: "",
                    tiptopDegree: "",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    // workState: "",
                    workID: "",
                    contractTerm: "",
                    conversionTime: "",
                    notWorkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                },
                defaultProps:{
                    children:'children',
                    label:"name"
                },
                rules:{
                    name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
                        pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                        message: '身份证号码格式不正确',
                        trigger: 'blur'
                    }],
                    wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
                    nationId: [{required: true, message: '请输入您组', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
                        type: 'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
                    address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请输入职位', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
                    workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
                    notworkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
                    beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
                    endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
                    workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}]
                }
            }
        },
        methods: {
            searchViewHandleNodeClick(data){
                this.inputDepName=data.name;
                this.advanceSearchValue.departmentId=data.id;
                this.popVisible=!this.popVisible;
            },
            onError(err,file,fileList){
                this.importDataBtnText="导入数据";
                this.importDataBtnIcon="el-icon-upload2";
                this.importDataDisabled=false;
                Message.error({message: '上传失败'})
            },
            onSuccess(response,file,fileList){
                this.importDataBtnText="导入数据";
                this.importDataBtnIcon="el-icon-upload2";
                this.importDataDisabled=false;
                Message.success({message: '上传成功'})
                this.initEmps();
            },
            beforeUpload(){
                this.importDataBtnText="正在导入";
                this.importDataBtnIcon="el-icon-loading";
                this.importDataDisabled=true;
            },
            exportData(){
                window.open("/employee/basic/export","_parent");
            },
            deleteEmp(data){
                this.$confirm('此操作将永久删除'+data.name+', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/basic/"+data.id).then(resp=>{
                        if (resp){
                            this.initEmps();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            resetEmp(){
                this.emp={
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: "",
                    nativePlace: "",
                    politicId: "",
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: "",
                    jobLevelId: "",
                    posId: "",
                    engageForm: "",
                    tiptopDegree: "",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    // workState: "",
                    workID: "",
                    contractTerm: "",
                    conversionTime: "",
                    notWorkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                };
                this.inputDepName="";
            },
            doAddEmp(){
                if (this.emp.id){
                    this.$refs["empForm"].validate(valid=>{
                        if (valid){
                            this.putRequest("/employee/basic/",this.emp).then(resp=>{
                                if (resp){
                                    this.dialogVisible=false;
                                    this.initEmps();
                                }
                            })
                        }
                    })
                }else{
                    this.$refs["empForm"].validate(valid=>{
                        if (valid){
                            this.postRequest("/employee/basic/",this.emp).then(resp=>{
                                if (resp){
                                    this.dialogVisible=false;
                                    this.initEmps();
                                }
                            })
                        }
                    })
                }
            },
            showDepView(){
                this.popVisible=!this.popVisible;
            },
            handleNodeClick(data){
                this.inputDepName=data.name;
                this.emp.departmentId=data.id;
                this.popVisible=!this.popVisible;
            },
            initData(){
                let nations=window.sessionStorage.getItem("nations");
                let joblevels=window.sessionStorage.getItem("joblevels");
                let politicsstatus=window.sessionStorage.getItem("politicsstatus");
                let deps=window.sessionStorage.getItem("deps");
                if (!nations){
                    this.getRequest("/employee/basic/nations").then(resp=>{
                        if(resp){
                            this.nations=resp;
                            window.sessionStorage.setItem("nations",JSON.stringify(resp));
                        }
                    })
                }else {
                    this.nations=JSON.parse(nations);
                }

                if (!joblevels){
                    this.getRequest("/employee/basic/joblevels").then(resp=>{
                        if(resp){
                            this.joblevels=resp;
                            window.sessionStorage.setItem("joblevels",JSON.stringify(resp));
                        }
                    })
                }else {
                    this.joblevels=JSON.parse(joblevels);
                }

                if (!politicsstatus){
                    this.getRequest("/employee/basic/politicsstatus").then(resp=>{
                        if(resp){
                            this.politicsstatus=resp;
                            window.sessionStorage.setItem("politicsstatus",JSON.stringify(resp));
                        }
                    })
                }else{
                    this.politicsstatus=JSON.parse(politicsstatus);
                }

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
            initPositions(){
                let positions=window.sessionStorage.getItem("positions");
                if (!positions){
                    this.getRequest("/employee/basic/positions").then(resp=>{
                        if(resp){
                            this.positions=resp;
                        }
                    })
                }
            },
            getMaxWorkID(){
                this.getRequest("/employee/basic/maxWorkID").then(resp=>{
                    if (resp){
                        this.emp.workID=resp.obj;
                    }
                })
            },
            showAddEmpView(){
                this.resetEmp();
                this.title="添加员工";
                this.dialogVisible=true;
                this.getMaxWorkID();
            },
            showEditEmpView(data){
                this.title="编辑员工信息";
                this.emp=data;
                this.inputDepName=data.department.name;
                this.dialogVisible=true;
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initEmps();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initEmps();
            },
            initEmps(type) {
                this.loading = true;
                let url = '/employee/basic/?page=' + this.page + '&size=' + this.size;
                if (type && type == 'advanced') {
                    if (this.advanceSearchValue.politicId) {
                        url += '&politicId=' + this.advanceSearchValue.politicId;
                    }
                    if (this.advanceSearchValue.nationId) {
                        url += '&nationId=' + this.advanceSearchValue.nationId;
                    }
                    if (this.advanceSearchValue.jobLevelId) {
                        url += '&jobLevelId=' + this.advanceSearchValue.jobLevelId;
                    }
                    if (this.advanceSearchValue.posId) {
                        url += '&posId=' + this.advanceSearchValue.posId;
                    }
                    if (this.advanceSearchValue.engageForm) {
                        url += '&engageForm=' + this.advanceSearchValue.engageForm;
                    }
                    if (this.advanceSearchValue.departmentId) {
                        url += '&departmentId=' + this.advanceSearchValue.departmentId;
                    }
                    if (this.advanceSearchValue.dateScope) {
                        url += '&dateScope=' + this.advanceSearchValue.dateScope;
                    }
                } else {
                    url += "&name=" + this.empName;
                }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.emps = resp.data;
                        this.total = resp.total;
                    }
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            resetAdvSearch(){
                this.showAdvanceSearchView=false;
                this.initEmps();
            }
        },
        mounted() {
            this.initEmps();
            this.initData();
            this.initPositions();
        }
    }
</script>

<style scoped>
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
</style>