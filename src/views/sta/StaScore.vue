<template>
    <div>
        <div style="margin-bottom: 20px;">
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
                    员工:
                    <el-select v-model="empID" :placeholder="inputDepName+'员工'" size="mini" style="width: 220px;">
                        <el-option
                                v-for="item in allDepEmps"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6">
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="getEmpEcPointByEmpID" :disabled="empID==null">搜索</el-button>
                        <el-button type="info" icon="el-icon-delete" size="mini" @click="resetSearch">重置</el-button>
                </el-col>
            </el-row>
        </div>

        <div id="myChart" :style="{width: '1000px', height: '600px'}"></div>
    </div>
</template>

<script>
    export default {
        name: "StaScore",
        data(){
            return{
                allDeps:[],
                allDepEmps:[],
                empID:null,
                depID:null,
                ecPoint:[9,7,0,8,2,5],
                ecDate:[2017,2018,2019,2020,2021,2022],
                popVisible:false,
                inputDepName:"所属部门",
                defaultProps:{
                    children:'children',
                    label:"name"
                }
            }
        },
        methods:{
            searchViewHandleNodeClick(data){
                this.inputDepName=data.name;
                this.depID=data.id;
                this.getALlEmpByDepID(this.depID);
                this.empID='';
                this.popVisible=!this.popVisible;
            },
            showDepView(){
                this.popVisible=!this.popVisible;
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
            getALlEmpByDepID(depID){
                this.getRequest("/statistics/score/dep?depID="+depID).then(resp=>{
                    if(resp){
                        this.allDepEmps=resp;
                    }
                })
            },
            getEmpEcPointByEmpID(){
                this.getRequest("/statistics/score/emp?empID="+this.empID).then(resp=>{
                    if(resp){
                        var ecDate=[];
                        var ecPoint=[];
                        for ( var i=0;i<resp.length;i++){
                            ecPoint.push(resp[i].ecPoint);
                            ecDate.push(resp[i].ecDate);
                        }
                        this.draw(ecPoint,ecDate);
                    }
                })
            },
            resetSearch(){
                this.depID=null;
                this.empID=null;
                this.inputDepName="所属部门";
                this.draw(this.ecPoint,this.ecDate);
            },
            draw(ecPoint,ecDate){
                // 初始化echarts实例
                let myChart = this.$echarts.init(document.getElementById('myChart'))
                // 绘制图表
                var option = {
                    xAxis: {
                        type: 'category',
                        data: ecDate
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: ecPoint,
                            type: 'line'
                        }
                    ]
                };
                //防止越界，重绘canvas
                window.onresize = myChart.resize;
                myChart.setOption(option);//设置option
            }
        },
        mounted() {
            this.draw(this.ecPoint,this.ecDate);
            this.initData();
        }
    }
</script>

<style scoped>

</style>