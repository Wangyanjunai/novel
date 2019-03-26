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
                                <th style="text-align: center">小说id</th>
                                <th style="text-align: center">文件名称</th>
                                <th style="text-align: center">标题</th>
                                <th style="text-align: center">作者</th>
                                <th style="text-align: center">出版社</th>
                                <th style="text-align: center">类型名称</th>
                                <th style="text-align: center">阅读人数</th>
                                <th style="text-align: center">创建时间</th>
                                <th style="text-align: center">修改时间</th>
                                <th colspan="2" style="text-align: center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list novelInfoPage.content as novelInfo>
                            <tr class="success">
                                <td>${novelInfo.id}</td>
                                <td>${novelInfo.fileName}</td>
                                <td>${novelInfo.title}</td>
                                <td>${novelInfo.author}</td>
                                <td>${novelInfo.publisher}</td>
                                <td>${novelInfo.categoryText}</td>
                                <td>${novelInfo.readers}</td>
                                <td>${novelInfo.createTime}</td>
                                <td>${novelInfo.updateTime}</td>
                                <td><a href="/novel/info/index?id=${novelInfo.id}">修改</a></td>
                                <td>
                                    <a href="/novel/info/delete?id=${novelInfo.id}">删除</a>
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
                                <li><a href="/novel/info/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                            </#if>
                            <#if novelInfoPage.totalPages gt 0>
                                <#list 1..novelInfoPage.totalPages as index>
                                    <#if currentPage == index>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/novel/info/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>
                            <#else>
                                <li class="disabled"><a href="#">${currentPage}</a></li>
                            </#if>
                            <#if currentPage gte novelInfoPage.totalPages>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/novel/info/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
