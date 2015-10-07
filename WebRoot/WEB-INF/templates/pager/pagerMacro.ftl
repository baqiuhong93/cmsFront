<#setting number_format="0"> 
<#macro pager_url_link linkurl listpn pn>
	<#if listpn == pn>
		<span class="current">${listpn}</span>
		<#else>
		<a href="${linkurl?replace('#pageNo',listpn)}">${listpn}</a>
	</#if>
</#macro>

<#macro frontPager _pager _pagerUrl>
<#if _pager??>
<div class="pagesNext mg20px">
	<div class="sabrosus">
	<#if _pager.pageSize gt 1>
		<#if _pager.pageNo gt 1>
			<a href="${_pagerUrl?replace('#pageNo',1)}">首页</a>
			<a href="${_pagerUrl?replace('#pageNo',_pager.pageNo-1)}">上一页</a>
		<#else>
			<span class="disabled">首页</span>
			<span class="disabled">上一页</span>
		</#if>
		
		<#if _pager.pageSize lt 12>
			<#list 1.. _pager.pageSize as pg>
				<@pager_url_link linkurl="${_pagerUrl}" listpn="${pg}" pn="${_pager.pageNo}" />
			</#list>
		<#else>
			<#list 1..3 as pg>
				<#if pg lte _pager.pageSize>
					<@pager_url_link linkurl="${_pagerUrl}" listpn="${pg}" pn="${_pager.pageNo}" />
				</#if>
			</#list>
			
			<#if _pager.pageNo == 3>
				<a href="${_pagerUrl?replace('#pageNo',_pager.pageNo+1)}">${_pager.pageNo+1}</a>
			</#if>
			<#if _pager.pageNo gt 5>
			..
			</#if>
			<#if _pager.pageNo gt 3 && _pager.pageNo lt _pager.pageSize-2>
				<#if _pager.pageNo-1 != 3>
					<@pager_url_link linkurl="${_pagerUrl}" listpn="${_pager.pageNo-1}" pn="${_pager.pageNo}" />
				</#if>
				<@pager_url_link linkurl="${_pagerUrl}" listpn="${_pager.pageNo}" pn="${_pager.pageNo}" />
				<#if _pager.pageNo+1 != _pager.pageSize-2>
				<@pager_url_link linkurl="${_pagerUrl}" listpn="${_pager.pageNo+1}" pn="${_pager.pageNo}" />
				</#if>
			</#if>
			<#if  _pager.pageNo lt _pager.pageSize-4>
			..
			</#if>
			<#if _pager.pageNo == _pager.pageSize-2>
				<a href="${_pagerUrl?replace('#pageNo',_pager.pageNo-1)}">${_pager.pageNo-1}</a>
			</#if>
			<#list _pager.pageSize-2.._pager.pageSize as pg>
				<#if pg lte _pager.pageSize>
					<@pager_url_link linkurl="${_pagerUrl}" listpn="${pg}" pn="${_pager.pageNo}" />
				</#if>
			</#list>
		</#if>
		<#if _pager.pageNo lt _pager.pageSize>
			<a href="${_pagerUrl?replace('#pageNo',_pager.pageNo+1)}">下一页</a>
			<a href="${_pagerUrl?replace('#pageNo',_pager.pageSize)}">末页</a>
			<#else>
			<a href="${_pagerUrl?replace('#pageNo',_pager.pageNo+1)}">上一页</a>
			<a href="${_pagerUrl?replace('#pageNo',_pager.pageSize)}">首页</a>
		</#if>
		<span>
			<select onchange="location.replace(this.value)">
				<#list 1.. _pager.pageSize as pg>
                	  <option value="${_pagerUrl?replace('#pageNo',pg)}">${pg}</option>
               	</#list>
            </select>
        </span>
        <span class="disabled">共${_pager.pageSize}页 共${_pager.pageCount}条</span>
	</#if>
	</div>
</div>
</#if>
</#macro>