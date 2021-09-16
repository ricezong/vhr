<template>
    <div>
    <div style="width: 400px;">
        <el-input
                placeholder="请输入部门名称信息搜索..."
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px;"
                v-model="filterText">
        </el-input>
        <el-tree
                :data="deps"
                :props="defaultProps"
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree">
                <span class="custom-tree-node" slot-scope="{ node, data }"
                      style="display: flex;justify-content: space-between;width: 100%">
                    <span style="line-height: 32px;font-size: 14px;">{{ node.label }}</span>
                    <span>
                        <el-button
                                type="text"
                                size="small"
                                @click="() => showAddDepView(data)">
                            添加部门
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                style="color: red"
                                @click="() => deleteDep(data)">
                            删除部门
                        </el-button>
                    </span>
                </span>
        </el-tree>
    </div>
    <div>
        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <template>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">上级部门</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" size="small" v-model="pname" disabled/>
                    </template>
                </template>
                <template>
                    <template>
                        <el-tag type="info" style="width: 19%;text-align: center;margin-bottom: 10px;">部门名称</el-tag>
                        <el-input style="width: 80%;margin-left: 1%" size="small" v-model="dep.name" placeholder="请输入部门名称..."/>
                    </template>
                </template>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" size="small">取 消</el-button>
                <el-button type="primary" @click="doAddDep" size="small">确 定</el-button>
          </span>
        </el-dialog>
    </div>
    </div>
</template>

<script>
    export default {
        name: "DepMana",
        data() {
            return {
                dialogVisible:false,
                filterText: '',
                dep:{
                  name:'',
                  parentId:-1
                },
                pname:'',
                deps: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        methods: {
            initDeps() {
                this.getRequest("/system/basic/department/").then(resp => {
                    if (resp) {
                        this.deps = resp;
                    }
                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            showAddDepView(data) {
                this.pname=data.name;
                this.dep.parentId=data.id;
                this.dialogVisible=true;
            },
            doAddDep(){
                this.postRequest("/system/basic/department/",this.dep).then(resp=>{
                    if (resp){
                        this.addDep2Deps(this.deps,resp.obj);
                        this.dialogVisible=false;
                        this.dep={
                            name:'',
                            parentId: -1
                        };
                        this.pname='';
                    }
                })
            },
            addDep2Deps(deps,dep){
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        d.children = d.children.concat(dep);
                        if (d.children.length > 0) {
                            d.parent = true;
                        }
                        return;
                    } else {
                        this.addDep2Deps(d.children, dep);
                    }
                }
            },
            deleteDep(data) {
                if (data.isParent) {
                    this.$message.error("父部门不可以删除");
                } else {
                    this.$confirm('此操作将永久删除' + data.name + '部门, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest("/system/basic/department/"+data.id).then(resp=>{
                            if (resp) {
                                this.removeDepFromDeps(null,this.deps,data.id);
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
            removeDepFromDeps(p,deps, id) {
                for(let i=0;i<deps.length;i++){
                    let d = deps[i];
                    if (d.id == id) {
                        deps.splice(i, 1);
                        if (deps.length == 0) {
                            p.parent = false;
                        }
                        return;
                    }else{
                        this.removeDepFromDeps(d,d.children, id);
                    }
                }
            }
        },
        mounted() {
            this.initDeps();
        }
    }
</script>

<style scoped>

</style>