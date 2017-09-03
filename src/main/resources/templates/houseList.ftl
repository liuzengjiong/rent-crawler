<html>  
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
</head>
<body> 
<style>
	
	.houseBlock{
		display:block;
		width:100%;
	}
	 * {
               margin: 0;
               padding: 0;
               }
            
    #parent >li> span{background: #999999;display: block;width: 200px;border:1px solid #ECEEF2;}
    li {line-height: 40px;display: block;}
    li  p{
            display: inline-block;
            width: 0px;
            height: 1em;
            border-left: 5px solid transparent;
            border-right: 5px solid transparent;
            border-top: 5px solid#2f2f2f;
              }
     li>ul{display: block;}
     li>ul>li{border: 1px solid #DEDEDE;width: 199px;}
     #parent span:hover + ul{display: block;}
     #parent span:hover >p{
             display: inline-block;
            width: 0px;
            height: 0px;
            border-top: 5px solid transparent;
            border-bottom: 5px solid transparent;
            border-left: 5px solid#2f2f2f;}
	
</style> 
${mail}，订阅的租房信息更新  <br />
  
      本次 搜索条件 ： ${searchCondition}  <br />
      本次房子数量：${houseSize} <br />
  <#assign rows=20 />
  <#if houseList?size != 0>
	   <ul id="parent">
		<#list houseList as house> 
			<#if (house_index % rows == 0)>
			
				<#assign tail=house_index+rows />
				<#if (tail>(houseList?size-1))>
					<#assign tail= houseList?size /> 
				</#if>
				<li>
	                <span><p>${house_index+1}到${tail}</p></span>
	                <ul>
	                
	                 <br/><br/>
			</#if>
			
			<li class="houseBlock">
				<h3>【${house_index+1}】  ${house.title!"未知"}</h3> <br />
				区域:${house.area!"未知"}  &nbsp;&nbsp;&nbsp;&nbsp;  小区:${house.garden!"未知"} <br />
				距离:${house.distance!"未知"} <br />
				类型:${house.roomType!"未知"}  &nbsp;&nbsp;&nbsp;&nbsp;   价格:${house.price!"未知"}  <br />
				发布时间:${house.sendTime!"未知"}    <br />
				<hr/>
				&nbsp;&nbsp;<a href="${house.url!"未知"}">查看详情</a><br/>
			</li>
			
			<#if (house_index % rows == rows-1) || !house_has_next >
			 	 </ul>
	            </li>
	            <br/><br/><br/><br/><br/><br/><br/><br/>
			</#if>
			
		</#list>
	  </ul>
  </#if> 
 
 <em class="clear">
 <br /> 
 <br /> 
${fromNickName} <br /> 
${time}  
  
</body>  
</html> 