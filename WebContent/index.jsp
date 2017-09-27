<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>goldenPoint</title>
</head>
<script type="text/javascript" src="../js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	function submit(){
		var num = document.getElementById("number").value;
		num*=1;
		if(num>=0&&num<=100){
			window.location.href="star?number="+num;
		}else
			alert("数字超出范围,请重新输入");
	}
</script>
<body>
	请输入0~100任意一个数字:<input name="number" id="number" type="text"/><br/>
	<input type="button" onclick="submit()" value="submit"/>
</body>
</html>