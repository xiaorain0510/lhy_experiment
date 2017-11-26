
<!DOCTYPE HTML>
<html>
<head>
<title>CLINICAL TEXT EXTRACTION</title>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Bootstrap -->
<link href="style/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="style/main.css" type="text/css">
<script src="style/js/bootstrap.min.js"></script>    
<link href="style/flat_ui/dist/css/flat-ui.css" rel="stylesheet">
<script src="style/jquery.min.js"></script>
<script src="style/dropzone/dropzone.js"></script>	
<link href="style/dropzone/dropzone.css" rel="stylesheet">
<script src="style/ajaxfileupload.js"></script>
	
<script>
$(document).ready(function(){
var tab = document.getElementById("labelTable");
var count = 0;

$("#upload").click(function(){
		$("#file").click();
	});

$("#file_div").change(function(){
	$("#content").text("正在上传解析...");
	 $.ajaxFileUpload
        ({
                url:'user_Upload',//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {              
                   var rowNum = tab.rows.length;
                   for(i=1;i<rowNum;i++){
                      tab.deleteRow(i);
                      rowNum = rowNum - 1;
                      i = i-1;
                   }
                   json = eval("("+data.result+")");                
                   for(var x in json){
                   	  if(x=="data"){
                   	  	$("#content").text(json[x]);
                   	  }else if(x!="info"&&x!="NA"){
                   	  	newRow = tab.insertRow();
                   	  	newCell11 = newRow.insertCell();
                   	  	newCell12 = newRow.insertCell();
                   	  	newCell11.innerHTML = x;
                   	  	newCell12.innerHTML = json[x];
                   	  	                  	  	
                   	  }
                   }              
                                                                                                
                },
                error: function (data, status)//服务器响应失败处理函数
                {
                   alert("出错了，请稍后再试");
                }
            });  
            
});	
	
});


</script>
     
</head>
 
 
<body>
 <nav class="navbar navbar-inverse">
            <div class="navbar-header">
                <a class="navbar-brand">CLINICAL TEXT EXTRACTION</a>
            </div>
        </nav>
        <div class="container" id="container1" style="margin-top:10px">
          <div class="row">
            <div class="col-md-6">
                <a class="btn btn-info btn-block" id="upload">上传</a>                   
                <p class="text-justify" style="margin-top:5%" id="content">
                    please upload .txt or .wav file
                </p>
            </div>            
            <div class="col-md-6">
                <table class="table table-bordered" id="labelTable">
                    <thead>
                        <tr class="active">
                            <th>Label</th>
                            <th>Text</th>                           
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>  
            </div>
          </div>
        </div>  
        <div id = "file_div"><input type="file" id="file" name="file" style="display:none; fopacity:0;" /></div> 
		   
</body>	
</html>