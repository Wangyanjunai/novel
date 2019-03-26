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
                    <!--表格数据-->
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover table-condensed">
                            <thead>
                            <tr>
                                <th style="text-align: center">类目名称</th>
                                <th style="text-align: center">类目id</th>
                                <th style="text-align: center">父级类目名称</th>
                                <th style="text-align: center">类目英文名称</th>
                                <th style="text-align: center">类目类型</th>
                                <th style="text-align: center">是否删除</th>
                                <th style="text-align: center">创建时间</th>
                                <th style="text-align: center">修改时间</th>
                                <th colspan="2" style="text-align: center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list categoryDTOPage.content as category>
                            <tr class="success">
                                <td>${ category.categoryName }</td>
                                <td>${ category.categoryId1 }</td>
                                <td>${ category.parentCategoryName }</td>
                                <td>${ category.categoryEnName }</td>
                                <td>${ category.categoryType }</td>
                                <td>
                                    <#if category.isDeleted == 0>
                                        否
                                        <#elseif category.isDeleted == 1>
                                        是
                                    </#if>
                                </td>
                                <td>${ category.createTime }</td>
                                <td>${ category.updateTime }</td>
                                <td><#if (category.parentCategoryId) ?? && category.parentCategoryId == ''>修改
                                    <#elseif (category.parentCategoryId) ??>
                                        <a href="/novel/category/index?categoryId=${ category.categoryId }">修改</a>
                                    </#if>
                                   </td>
                                <td>
                                    <#if (category.parentCategoryId) ?? && category.parentCategoryId == ''>删除
                                        <#elseif (category.parentCategoryId) ??>
                                            <#if category.isDeleted == 0>
                                                    <a href="/novel/category/delete?categoryId=${ category.categoryId }">删除</a>
                                                <#elseif category.isDeleted == 1>删除
                                            </#if>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <!--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/novel/category/list?page=${ currentPage - 1}&size=${ size }">上一页</a></li>
                            </#if>
                            <#if categoryDTOPage.totalPages gt 0>
                                <#list 1..categoryDTOPage.totalPages as index>
                                    <#if currentPage == index>
                                        <li class="disabled"><a href="#">${ index }</a></li>
                                    <#else>
                                        <li><a href="/novel/category/list?page=${ index }&size=${ size }">${ index }</a></li>
                                    </#if>
                                </#list>
                            <#else>
                                <li class="disabled"><a href="#">${ currentPage }</a></li>
                            </#if>
                            <#if currentPage gte categoryDTOPage.totalPages>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/novel/category/list?page=${ currentPage + 1 }&size=${ size }">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
