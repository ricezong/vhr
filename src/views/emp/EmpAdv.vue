<template>
    <div>
        <el-tabs v-model="activeName">
            <el-tab-pane label="奖惩资料" name="ecmana"><ec-mana/></el-tab-pane>
            <el-tab-pane label="培训资料" name="trainmana"><train-mana/></el-tab-pane>
            <el-tab-pane label="调薪资料" name="adjustmana"><ad-just-mana/></el-tab-pane>
            <el-tab-pane label="调动资料" name="removemana"><remove-mana/></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import EcMana from "../../components/emp/adv/EcMana";
    import TrainMana from "../../components/emp/adv/TrainMana";
    import AdJustMana from "../../components/emp/adv/AdJustMana";
    import RemoveMana from "../../components/emp/adv/RemoveMana";



    export default {
        name: "EmpAdv",
        data(){
            return{
                activeName:'ecmana'
            }
        },
        components:{
            'EcMana':EcMana,
            'TrainMana':TrainMana,
            'AdJustMana':AdJustMana,
            'RemoveMana':RemoveMana
        }
    }
</script>

<style scoped>

</style>