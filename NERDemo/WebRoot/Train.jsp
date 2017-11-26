
<!DOCTYPE HTML>
<html>
<head>
<title>TRAIN YOUR MODEL</title>
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

$("#train").click(function(){
		$.ajax({
			type:"post",
			url:"user_Train",
			data:null,
			async:false,
			success:function(data){				
				//code = eval("("+data.result+")").random_code//得到验证码的内容	
			},
			error:function(data){				
			}
		});       
	});
	
});


</script>
     
</head>
 
 
<body>
 <nav class="navbar navbar-inverse">
            <div class="navbar-header">
                <a class="navbar-brand">TRAIN YOUR MODEL</a>
            </div>
        </nav>
        <div class="container" id="container1" style="margin-top:10px">
          <div class="row">
            <div class="col-md-6">
                <a class="btn btn-info btn-block" id="train">训练</a>     
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
		   
</body>	
</html>