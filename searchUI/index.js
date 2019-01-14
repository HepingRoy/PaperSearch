import React from 'react'
import { render } from 'react-dom'
import {notification,Table, Input,Button,Tag,Row,Col,Pagination,Layout,Icon} from 'antd'
const { Header, Content,Footer} = Layout
import './index.css'
import './fonts/antd_icon_font/iconfont.css'

var SEARCH = React.createClass({
    getInitialState: function () {
        return {
            value: "",
            flag: true,
            row: 0,
            size: 8,
            data: null,
            source:null,
        };
    },
    handleChange: function (e) {
        this.setState({value: e.target.value})
    },
    handlePage:function (page, pageSize){
    	this.setState({data:this.state.source.slice((page-1)*pageSize,page*pageSize)})
    },
    handleDataSource:function(data){
    	var url = "/solr/query.do";
    	  CALLJSON(url,data,function (e) {
              if(e.code==1){
                  var dataSource = [];
                  console.log("success")
                  for(var i =0;i<e.rows.length;i++){
                      console.log(i)
                      var ob = {key:i,data:{author:e.rows[i]["author"],url:e.rows[i]["url"],title:e.rows[i]["title"],label:e.rows[i]["label"],content:e.rows[i]["content"],type:(e.rows[i]["title"]).split(".")[1]}};
                      dataSource.push(ob);
                      console.log(dataSource)
                  }
                  this.setState({row:e.total,data:dataSource.slice(0,this.state.size),flag: false,source:dataSource})
                  console.log(this.state.data)
              }
          }.bind(this),function () {
              this.setState({flag: false});
          }.bind(this))
    },
    handleSearch: function () {
    	var keyword = (this.state.value).replace(/^[\s　]+|[\s　]+$/g, "");
        if (keyword != "") {
        	if(keyword.length<2){
        		this.message("warning","warning","请至少输入2个关键字");
        		return;
        	}
            var data = {params:{keyword:keyword}}
              this.handleDataSource(data);
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
    message:function(type,message,desc){
    	notification[type]({
    	    message: message,
    	    description: desc,
    	    duration:3,
    	  });
    },
    render: function () {
    	var empty =<div>
            <div><Icon type="login"/><Icon type="logout"/><Icon type="upload"/></div>
            <div style={{width: "100%", "marginTop": "150px"}}>
            <div style={{textAlign:'center'}}>
                <span style={{fontSize:27,color:'blue',marginRight:'5px'}}>Paper</span>
                <span style={{fontSize:27,color:'red',marginRight:'55px'}}>Search</span>
            </div>
            <br/><br/>
            <div style={{margin: "0 auto", width: "38%"}}>
                <Input.Group compact>
                    <Input size="large"
                                onPressEnter={this.handleSearch}
                                value={this.state.value}
                                placeholder="Search"
                                onChange={this.handleChange}
                                style={{width: '85%',height:'42px'}}/>
                    <Button size="large"
                                 onClick={this.handleSearch}
                                 style={{width: '14%',height:'42px'}}
                                 type="primary">搜索
                    </Button>
                </Input.Group>
            </div>
        </div>
        </div>
        var columns = [{
            title: '',
            dataIndex: 'data',
            width: '66%',
            render: function (text, record, index) {

                var lab = this.state.data[index]['data']['label']+","+this.state.data[index]['data']['author'];
                var filekey ="/solr/download.do?fileId="+encodeURI(encodeURI(this.state.data[index]['data']['url']));
                var pic ="javascript:pic('"+this.state.data[index]['data']['title']+"')";
            	return (<div>
                    <Row>
                        <Col>
                            <span style={{'font-size': '22px'}}
                                  className={this.fileIcon($(this.state.data[index]['data']['type']))}/>
                            <a href={pic}
                               style={{
                                'margin-left': '15px',
                                'font-size': '22px',
                                color: '#00c'
                            }} dangerouslySetInnerHTML={{__html:this.state.data[index]['data']['title']}}/>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <div style={{'padding-top': '8px'}}
                                 className="light-font content" dangerouslySetInnerHTML={{__html: this.state.data[index]['data']['content']}}/>
                        </Col>
                    </Row>
                    <Row align="bottom">
                        <Col span={22}>
                            <div style={{'padding-top': '10px'}}>
                            {lab}
                            </div>
                        </Col>
                        <Col span={2}>
                            <a style={{'font-size': '22px', 'padding-top': '10px'}}
                               className="icon anticon icon-download" href={filekey}></a>
                        </Col>
                    </Row>
                </div>)
            }.bind(this)
        }]
        var footer = '';
        if (this.state.row > this.state.size) {
            footer = <Footer>
                <div style={{'padding-left': '188px', width: '60%'}}>
                    <Pagination
                        size="large"
                        total={this.state.row}
                        onChange={this.handlePage}
                        pageSize={this.state.size}
                       current={this.state.page}
                    />
                </div>
            </Footer>
        }
        var notEmpty = <Layout style={{background: '#fff'}}>
            <Header style={{zIndex: 999, position: 'fixed', width: '100%', background: '#f7f7f7'}}>
                <div style={{margin: "11px 0px", width: "35%"}}>
                    <Input.Group compact>
                        <Input size="large"
                                    onPressEnter={this.handleSearch}
                                    value={this.state.value}
                                    onChange={this.handleChange}
                                    style={{width: '75%',height:'42px'}}/>
                        <Button size="large"
                                     onClick={this.handleSearch}
                                     style={{width: '14%',height:'42px'}}
                                     type="primary">搜索
                        </Button>
                    </Input.Group>
                </div>
            </Header>
            <Content style={{padding: '0 15px', marginTop: 78}}>
                <div style={{width: '55%'}}>
                    <Table pagination={false}
                                showHeader={false}
                                dataSource={this.state.data}
                                columns={columns}/>
                </div>
            </Content>
            {footer}
        </Layout>
        return (
            this.state.flag ? empty : notEmpty
        );
    }
});

render(<SEARCH/>, document.getElementById('index'));
