<template>
    <div>
        <el-tabs v-model="activeName" type="border-card">
            <el-tab-pane label="部门管理" name="depmana"><dep-mana/></el-tab-pane>
            <el-tab-pane label="职位管理" name="posmana"><pos-mana/></el-tab-pane>
            <el-tab-pane label="职称管理" name="joblevelmana"><job-level-mana/></el-tab-pane>
            <el-tab-pane label="权限组" name="permissmana"><permiss-mana/></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import DepMana from "../../components/sys/basic/DepMana";
    import JobLevelMana from "../../components/sys/basic/JobLevelMana";
    import PermissMana from "../../components/sys/basic/PermissMana";
    import PosMana from "../../components/sys/basic/PosMana";

    export default {
        name: "SysBasic",
        data(){
            return{
                activeName:'permissmana'
            }
        },
        components:{
            'DepMana':DepMana,
            'JobLevelMana':JobLevelMana,
            'PermissMana':PermissMana,
            'PosMana':PosMana
        }
    }
</script>

<style scoped>

</style>