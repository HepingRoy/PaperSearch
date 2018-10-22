var EMPTY = React.createClass({
    getInitialState: function () {
        return {
            value: "",
            flag: true,
            row: 0,
            size: 10,
            start: 1,
            data: null,
        };
    },

    componentDidMount: function () {

    },
    handleChange: function (e) {
        this.setState({value: e.target.value})
    },
    handleSearch: function () {
        if (this.state.value != "") {
            var url = "/solr/query.do";
            var data = {start: this.state.start - 1, rows: this.state.size,params:{keyword:this.state.value}}
            CALLJSON(url,data,function (e) {
                if(e.code==1){
                    var dataSource = [];
                    for(var i =0;i<e.rows.length;i++){
                        var ob = {key:i,data:{name:e.rows[i]["title"],time:e.rows[i]["createTime"],content:e.rows[i]["content"],type:(e.rows[i]["title"]).split(".")[1]}};
                        dataSource.push(ob);
                    }
                    console.log(dataSource);
                    this.setState({row:e.total,data:dataSource,flag: false})
                }
            }.bind(this),function () {
                this.setState({flag: false});
            }.bind(this))
        } else {
            this.setState({flag: true})
        }
    },
    fileIcon: function (type) {
        if (type == "pdf") {
            return "icon anticon icon-pdffile1";
        } else if (type == "doc" || type == "docx") {
            return "icon anticon icon-wordfile1";
        } else if (type == "xlsx" || type == "xls") {
            return "icon anticon icon-exclefile1";
        } else return "icon anticon icon-filetext1";
    },
    render: function () {
        var empty = <div style={{width: "100%", "margin-top": "150px"}}>
            <div style={{margin: "0 auto", width: "38%"}}>
                <antd.Input.Group compact>
                    <antd.Input size="large"
                                onPressEnter={this.handleSearch}
                                value={this.state.value}
                                placeholder="Search"
                                onChange={this.handleChange}
                                style={{width: '85%'}}/>
                    <antd.Button size="large"
                                 onClick={this.handleSearch}
                                 style={{width: '14%'}}
                                 type="primary">搜索
                    </antd.Button>
                </antd.Input.Group>
            </div>
        </div>
        var columns = [{
            title: '',
            dataIndex: 'data',
            width: '66%',
            render: function (text, record, index) {
                return (<div>
                    <antd.Row>
                        <antd.Col>
                            <span style={{'font-size': '28px'}}
                                  className={this.fileIcon(this.state.data[index]['data']['type'])}/>
                            <a style={{
                                'margin-left': '15px',
                                'font-size': '22px',
                                color: '#00c'
                            }}>{this.state.data[index]['data']['name']}</a>
                        </antd.Col>
                    </antd.Row>
                    <antd.Row>
                        <antd.Col>
                            <div style={{'padding-top': '8px'}}
                                 className="light-font content" dangerouslySetInnerHTML={{__html: this.state.data[index]['data']['content']}}/>
                        </antd.Col>
                    </antd.Row>
                    <antd.Row align="bottom">
                        <antd.Col span={22}>
                            <div style={{'padding-top': '10px'}}>
                                {this.state.data[index]['data']['time']}
                            </div>
                        </antd.Col>
                        <antd.Col span={2}>
                            <a style={{'font-size': '22px', 'padding-top': '10px'}}
                               className="icon anticon icon-download"></a>
                        </antd.Col>
                    </antd.Row>
                </div>)
            }.bind(this)
        }]
        var footer = '';
        if (this.state.row > this.state.size) {
            footer = < antd.Layout.Footer>
                <div style={{'padding-left': '188px', width: '60%'}}>
                    <antd.Pagination
                        size="large"
                        total={this.state.row}
                        onChange={function (page, pageSize) {
                            console.log(page);
                            consolr.log(pageSize);
                        }}
                        pageSize={this.state.size}
                        defaultCurrent={1}
                    />
                </div>
            </antd.Layout.Footer>
        }
        var notEmpty = <antd.Layout style={{background: '#fff'}}>
            <antd.Layout.Header style={{zIndex: 999, position: 'fixed', width: '100%', background: '#f7f7f7'}}>
                <div style={{margin: "1px 80px", width: "38%"}}>
                    <antd.Input.Group compact>
                        <antd.Input size="large"
                                    onPressEnter={this.handleSearch}
                                    value={this.state.value}
                                    onChange={this.handleChange}
                                    style={{width: '85%'}}/>
                        <antd.Button size="large"
                                     onClick={this.handleSearch}
                                     style={{width: '14%'}}
                                     type="primary">搜索
                        </antd.Button>
                    </antd.Input.Group>
                </div>
            </antd.Layout.Header>
            <antd.Layout.Content style={{padding: '0 100px', marginTop: 64}}>
                <div style={{width: '60%'}}>
                    <antd.Table pagination={false}
                                showHeader={false}
                        //loading={loading}
                        //bordered
                                dataSource={this.state.data}
                                columns={columns}/>
                </div>
            </antd.Layout.Content>
            {footer}
        </antd.Layout>
        return (
            this.state.flag ? empty : notEmpty
        );
    }
});

ReactDOM.render(
    <EMPTY/>,
    document.getElementById('example')
);
