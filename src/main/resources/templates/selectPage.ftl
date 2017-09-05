<html>  
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
</head>
<body> 
 <br />
  ${mail},你好 <br />	<br />	
  <#if points?size != 0>
  		已订阅列表: <br />
	   <ul id="parent">
		<#list points as point> 
			
			<li class="houseBlock">
				【${point_index+1}】  地铁：${point.stationName!"没有要求"}</h3> <br />
				房间类型：${point.roomType!"没有要求"}  <br />
				只看最近更新 ：${point.nearlyUpdate?string ("是","否")}
				<#if point.priceFrom != 0 || point.priceTo != 0 >
				 <br />  价格区间：${point.priceFrom} - ${point.priceTo}
				</#if>
				&nbsp;&nbsp;<a href="${deleteUrl}/${mail}/${point.id}">取消订阅</a><br/>
				<hr/>
			</li>
			
		</#list>
	  </ul>
  </#if> 
  <br />
  <br />
       新增订阅：<br />
  <form action="${addUrl}" >
  	<input type="hidden" name="mail" value="${mail}" />
  	地铁：<input type="text" name="stationName" /> * <br />
  	户型：<input type="text" name="roomType" /> <br />
  	价格：<input type="text" name="priceFrom" />  -  <input type="text" name="priceTo" />　<br />
  	只看最近更新：<input type="radio" name="nearlyUpdate" value="true" />是 &nbsp;&nbsp;<input type="radio" name="nearlyUpdate" value="false" checked />否
  	<input type="submit" value="提交" />
  </form>
  
  <br /> <br /> <br />
  备注：
 可选地铁列表：${stationListStr}
 
</body>  
</html> 