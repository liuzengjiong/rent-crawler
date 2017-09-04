<html>  
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
</head>
<body> 
 <br />
  
  <#if points?size != 0>
  		已订阅列表: <br />
	   <ul id="parent">
		<#list points as point> 
			
			<li class="houseBlock">
				【${point_index+1}】  地铁：${point.stationName!"没有要求"}</h3> <br />
				房间类型：${point.roomType!"没有要求"}  <br />
				<#if point.priceFrom != 0 || point.priceTo != 0 >
				价格区间：${point.priceFrom} - ${point.priceTo} <br />
				</#if>
				<hr/>
				&nbsp;&nbsp;<a href="${deleteUrl}/${mail}/${point.id}">取消订阅</a><br/>
			</li>
			
		</#list>
	  </ul>
  </#if> 
  <br />
  <br />
       新 增订阅：<br />
  <form action="${addUrl}" >
  	<input type="hidden" name="mail" value="${mail}" />
  	地铁：<input type="text" name="stationName" /> * <br />
  	户型：<input type="text" name="roomType" /> <br />
  	价格：<input type="text" name="priceFrom" />  -  <input type="text" name="priceTo" />　<br />
  	<input type="submit" value="提交" />
  </form>
  
  <br /> <br /> <br />
  备注：
 可选地铁列表：${stationListStr}
 
</body>  
</html> 