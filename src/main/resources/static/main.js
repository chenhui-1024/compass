const axiosInstance = axios.create(
    {
        baseURL: "http://localhost:8024/ch-compass",
        timeout: 5000,
        auth: {
            username: JSON.parse(window.localStorage.getItem('loggedAccount')).username,
            password: JSON.parse(window.localStorage.getItem('loggedAccount')).password
        },
        validateStatus: function (status) {
            return status >= 200 && status < 500;
        }
    }
)

axiosInstance.interceptors.response.use(rsp=>{
 if(rsp.status===401){
    window.localStorage.clear()
    window.location.href='login.html'
  }

  if(rsp.status>=200 && rsp.status<500){
    return rsp
  }
})

new Vue({
    el: '#app',
    data: function () {
        return {
            divName: 'decision-table-list',
            dtPage: {},
            dt: {},
            baseUrl: 'http://localhost:8080',
            types: ['NUMBER', 'TEXT','BOOL','DATE'],
            dtNameMaxLength: 25,
            dtDescriptionMaxLength: 140,
            variableNameMaxLength: 15,
            ruleKeyNameLength: 20,
            precision: 3,
            NUMBERrStep: 0.001,
            verifyResult: null,
            testCases: [],
            loggedAccount: {},
            accounts: [],
            account: {},
            privileges: [
                {name: '创建决策表', value: 'create_decision_table'},
                {name: "编辑决策表", value: "edit_decision_table"},
                {name: "删除决策表", value: "delete_decision_table"}
            ],
            testInput: {},
            testResult: {},
            userNameMaxLength: 15,
            userPasswordLength: 8,
            emailMaxLength: 30,
            dtStatuses: [{name: '草稿', value: 'DRAFT'}, {name: '已发布', value: 'PUBLISHED'}, {name: '已下线', value: 'OFF'}]
        }
    },
    methods: {
        createDecisionTable: function () {
            this.divName = 'config-decision-table'
            this.dt = {name: '', description: '', inputVariables: [], outputVariables: [], rules: []}
        },
        cancelCreateTable: function () {
            this.divName = 'decision-table-list'
            this.doRefreshDtList()
        },
        doSaveDt: function () {
            console.log("dt:"+JSON.stringify(this.dt))

            if (this.dt.id) {
                axiosInstance
                    .post(this.baseUrl + "/decision-tables/" + this.dt.id, this.dt)
                    .then(rsp => {
                        if (rsp.status == 200) {
                            this.showMessage("成功更新决策表", 'success')
                            this.doRefreshDt()
                        } else {
                            this.showMessage("决策表更新失败:" + rsp.data.message, 'error')
                        }
                    })
            } else {
                axiosInstance
                    .post("/decision-tables/", this.dt)
                    .then(rsp => {
                        if (rsp.status == 200) {
                            this.showMessage('成功创建决策表', 'success')
                            this.divName='decision-table-list'
                            this.doRefreshDtList()
                        } else {
                            this.showMessage('创建决策表失败：' + rsp.data.message, 'error')
                        }
                    })
            }
        },
        removeOutput: function (index) {
            this.dt.outputVariables.splice(index, 1)
        },
        addOutput: function () {
            this.dt.outputVariables.push({name: '', type: 'NUMBER'})
        },
        removeInput: function (index) {
            this.dt.inputVariables.splice(index, 1)
        },
        addInput: function () {
            this.dt.inputVariables.push({name: '', type: 'NUMBER'})
        },
        addRule: function () {
            this.dt.rules.push({key: '', expression: '', decision: {}})
        },
        removeRule: function (index) {
            this.dt.rules.splice(index, 1)
        },
        doRefreshDtList: function () {
            axiosInstance
                .get("/decision-tables/")
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.dtPage = JSON.parse(JSON.stringify(rsp.data))
                    } else {
                        this.showMessage(rsp.data.message, 'error')
                    }
                })
        },
        doDeleteDt: function (dtId) {
            this.$confirm('此操作将删除该决策表, 是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axiosInstance.delete("/decision-tables/" + dtId).then(rsp => {
                    if (rsp.status == 200) {
                        this.showMessage("删除成功", 'success')
                        this.doRefreshDtList()
                    } else {
                        this.showMessage('操作失败请重试', 'error')
                    }
                })
            })
        },
        editDt: function (index) {
            this.dt = JSON.parse(JSON.stringify(this.dtPage.data[index]))
            this.divName = 'config-decision-table'
        },
        logout: function () {
            window.localStorage.clear()
            this.loggedAccount = null
            window.location.href = "login.html"
        },
        handleMenuCommand: function (command) {
            switch (command) {
                case "decisionTables":
                    this.doRefreshDtList()
                    this.divName = 'decision-table-list'
                    return;
                case "users":
                    this.divName = 'account-list'
                    this.doRefreshAccounts()
                    return
                case "logout":
                    this.logout()
            }
        },
        doRefreshAccounts: function () {
            axiosInstance
                .get('/accounts')
                .then(rsp => {
                    if (rsp.status === 200) {
                        this.accounts = JSON.parse(JSON.stringify(rsp.data))
                    } else {
                        this.showMessage('刷新用户列表失败请重试')
                    }
                })
        },
        createAccount: function () {
            if (this.account.username == null || this.account.username.length == 0) {
                this.$message('用户名不能为空')
                return
            }

            if (this.account.password == null || this.account.password.length == 0) {
                this.$message('密码须设置')
                return;
            }

            if (this.account.password != this.account.confirmedPassword) {
                this.$message('两次输入密码不一致')
                return;
            }

            axiosInstance
                .post('/accounts', this.account)
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.showMessage('用户创建成功', 'success')
                        this.doRefreshAccounts()
                        this.account = {}
                        this.divName = 'account-list'
                    } else {
                        this.showMessage(rsp.data.message, 'error')
                    }
                })
        },
        updateAccount: function () {
            axiosInstance
                .post('/accounts/' + this.account.username, this.account)
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.showMessage('更新用户信息成功', 'success')
                    } else {
                        this.showMessage('操作失败请重试', 'error')
                    }
                })
        },
        editAccount: function (username) {
            this.doRefreshAccount(username)
            this.divName = 'show-account'
        },
        doRefreshAccount: function (username) {
            axiosInstance
                .get("/accounts/" + username)
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.account = rsp.data;
                    }
                })
        },
        gotoAccounts: function () {
            this.doRefreshAccounts();
            this.account = {}
            this.divName = 'account-list'
        },
        showMessage: function (message, type) {
            this.$message({
                message: message,
                type: type
            })
        },
        showTestDecisionTable: function (decisionTableId) {
            axiosInstance
                .get(this.baseUrl + "/decision-tables/" + decisionTableId)
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.dt = JSON.parse(JSON.stringify(rsp.data))
                        this.divName = 'test-decision-table'
                    } else {
                        this.showMessage(rsp.message, 'error')
                    }
                })
        },
        runTest: function () {
            axiosInstance
                .post("/decision-tables/" + this.dt.id + "/execution?force=true", this.testInput)
                .then(rsp => {
                    if (rsp.status == 200) {
                        this.testResult = JSON.parse(JSON.stringify(rsp.data))
                    } else {
                        this.showMessage(rsp.data.message, 'error')
                    }
                })
        },
        doRefreshDt: function () {
            axiosInstance
                .get('/decision-tables/' + this.dt.id)
                .then(rsp => {
                    if (rsp.status === 200) {
                        this.dt = JSON.parse(JSON.stringify(rsp.data))
                    }
                })
        },
        gotoDtList: function () {
            this.testInput = {}
            this.testResult = {}
            this.dt = {}
            this.doRefreshDtList()
            this.divName = 'decision-table-list'
        },
        publishDt: function () {
            this.$confirm('该操作将允许通过接口访问决策表，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axiosInstance
                    .post('/decision-tables/' + this.dt.id, {version: this.dt.version, status: 'PUBLISHED'})
                    .then(rsp => {
                        if (rsp.status === 200) {
                            this.showMessage('成功发布', 'success')
                            this.doRefreshDt()
                        } else {
                            this.showMessage('操作失败请重试', 'warning')
                        }
                    })
            })

        },
        offDt: function () {
            this.$confirm('下线后造成接口无法正常访问决策表，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axiosInstance
                    .post('/decision-tables/' + this.dt.id, {version: this.dt.version, status: 'OFF'})
                    .then(rsp => {
                        if (rsp.status === 200) {
                            this.showMessage('成功下线', 'success')
                            this.doRefreshDt()
                        } else {
                            this.showMessage('操作失败请重试', 'warning')
                        }
                    })
            })
        },
        dtStatusNameMapping: function (statusValue) {
            return this.dtStatuses.find(s => s.value === statusValue).name
        }
    },
    computed: {},
    mounted: function () {
        this.loggedAccount = JSON.parse(window.localStorage.getItem("loggedAccount"))

        axiosInstance
            .get(this.baseUrl + "/decision-tables/")
            .then(rsp => {
                if (rsp.status == 200) {
                    this.dtPage = JSON.parse(JSON.stringify(rsp.data))
                } else {
                    this.showMessage('获取决策列表失败请重试', 'error')
                }
            })
    }
})


