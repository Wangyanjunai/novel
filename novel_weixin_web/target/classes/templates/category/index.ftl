<html>
    <#include "../common/header.ftl">
<body>
	<div id="wrapper" class="toggled">
		<#--边栏 sidebar-->
		<#include "../common/nav.ftl">
		<#--主体内容区域 content-->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<form role="form" method="post" action="/novel/category/save">
							<div class="form-group">
								 <label>类目名称</label><input name="categoryName" type="text" class="form-control" value="${(categoryDTO.categoryName) !''}" />
							</div>
							<div class="form-group">
								<label>类目英文名称</label><input name="categoryEnName" type="text" class="form-control" value="${(categoryDTO.categoryEnName) !''}" />
							</div>
							<div class="form-group">
								<label>父级类目</label>
								<select name="parentCategoryId" class="form-control">
									<#list categoryDTOList as parentCategoryDTO>
										<option value="${parentCategoryDTO.categoryId}"
												<#if (categoryDTO.parentCategoryId)?? && (parentCategoryDTO.categoryId)?? && categoryDTO.parentCategoryId == parentCategoryDTO.categoryId>selected</#if>>${parentCategoryDTO.categoryName}
										</option>
									</#list>
								</select>
							</div>
							<div class="form-group">
								 <label>类目类型</label>
								 <input name="categoryType" type="number" class="form-control" value="${(categoryDTO.categoryType) !''}" />
							</div>
							<div class="form-group">
								<label>是否删除</label>
								<select name="isDeleted" class="form-control">
									<option value="0" <#if (categoryDTO.isDeleted)?? && categoryDTO.isDeleted == 0> selected</#if>>否</option>
									<option value="1" <#if (categoryDTO.isDeleted)?? && categoryDTO.isDeleted == 1> selected</#if>>是</option>
								</select>
							</div>
							<input hidden type="text" name="categoryId" value="${(categoryDTO.categoryId) !''}">
							<button type="submit" class="btn btn-default">提交</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
