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
                        <div class="form-group" <#if (bookInfo.bookId) ?? && (bookInfo.bookId) != ''>style="display: none;" </#if>>
                            <form id="uploadForm" role="form" action="/novel/info/upload" method="POST" enctype="multipart/form-data">
                                <label for="inputFile">选择小说电子书：</label>
                                <div class="file-loading">
                                    <input id="file-0c" name="file" language="zh" type="file" data-show-preview="false" showUpload="false" showCancel="true" progressThumbClass="progress-bar progress-bar-success progress-bar-striped active" progressClass="" progressCompleteClass="progress-bar progress-bar-success" progressErrorClass="progress-bar progress-bar-danger" progressUploadThreshold="100" data-allowed-file-extensions='["epub"]'>
                                </div>
                                <div class="form-group">
                                    <label for="inputFileCategory">小说类目：</label>
                                    <select name="categoryEnName" class="form-control">
                                        <#list categoryList as category>
                                            <option <#if categoryEnName?? && categoryEnName == category.categoryEnName>selected="selected"</#if> value="${category.categoryEnName}">${category.categoryName}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="inputFileNovelStatus">更新状态：</label>
                                    <select name="novelStatus" class="form-control">
                                       <option value="0">连载中</option>
                                       <option value="1">完结</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-default">上传</button>
                            </form>
                        </div>
                        <form id="addForm" role="form" action="/novel/info/save" method="POST">
                            <div class="form-group">
                                <label for="inputFileName">文件名称：</label>
                                <input type="text" value="${(bookInfo.fileName) !''}" name="fileName" placeholder="文件名称" class="form-control" id="inputFileName" readonly="readonly" />
                                <input type="hidden" value="${(bookInfo.id) !''}" name="id" class="form-control" id="inputFileId" />
                            </div>
                            <div class="form-group">
                                <label for="inputAuthor">作者：</label>
                                <input type="text" value="${(bookInfo.author) !''}" name="author" placeholder="作者" class="form-control" id="inputAuthor" readonly="readonly" />
                            </div>
                            <div class="form-group">
                                <label for="inputTitle">标题：</label>
                                <input type="text" value="${(bookInfo.title) !''}" name="title" placeholder="标题" class="form-control" id="inputTitle" readonly="readonly" />
                            </div>
                            <div class="form-group">
                                <label for="inputTitle">简介：</label>
                                <textarea name="introduction" id="inputIntroduction" placeholder="简介" class="form-control" onkeyup="cal();" maxlength="100" onpropertychange="if(value.length>100) value=value.substr(0, 100)">${(bookInfo.introduction) !''}</textarea>
                                <span class="text_count">还能输入<span class="num_count" id="numCount"></span>字。（包括标点符号）</span>
                            </div>
                            <div class="form-group">
                                <label for="inputPublish">出版社：</label>
                                <input type="text" value="${(bookInfo.publisher) !''}" name="publish" placeholder="出版社" class="form-control" id="inputPublish" />
                            </div>
                            <div class="form-group">
                                <label for="inputCover">封面图片：</label>
                                <img src="${(bookInfo.cover) !''}" width="120px" height="160px">
                                <input type="hidden" value="${(bookInfo.cover) !''}" name="cover" placeholder="封面图片" class="form-control" id="inputCover" />
                            </div>
                            <button type="submit" class="btn btn-default">添加</button>
                        </form>
                    </div>
                </div>
			</div>
		</div>
	</div>
    <link href="/novel/css/fileInput.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="/novel/js/common/jquery-1.9.1.min.js" crossorigin="anonymous" type="text/javascript"></script>
    <script src="/novel/js/fileInput.js" crossorigin="anonymous" type="text/javascript"></script>
    <script src="/novel/js/zh.js" crossorigin="anonymous" type="text/javascript"></script>
    <script crossorigin="anonymous" type="text/javascript">
        $(function(){
            var length = $("#inputIntroduction").val().length;
            $("#numCount").text(100 - length);
            var oFileInput = new FileInput();
            oFileInput.Init("file-0c", "/api/OrderApi/ImportOrder");
        });
        function cal(){
            var length = $("#inputIntroduction").val().length;
            if(length > 100){
                $("#text_count").text(length);
                $("#numCount").text(0);
            }else{
                $("#numCount").text(100 - length);
            }
        };
        //初始化fileinput
        var FileInput = function () {
            var oFile = new Object();
            //初始化fileinput控件（第一次初始化）
            oFile.Init = function(ctrlName, uploadUrl) {
                var control = $('#' + ctrlName);
                //初始化上传控件的样式
                control.fileinput();
                //导入文件上传完成之后的事件
                $("#file-0c1").on("fileuploaded", function (event, data, previewId, index) {
                    $("#myModal").modal("hide");
                    var data = data.response.lstOrderImport;
                    if (data == undefined) {
                        toastr.error('文件格式类型不正确');
                        return;
                    }
                    //1.初始化表格
                    var oTable = new TableInit();
                    oTable.Init(data);
                    $("#progress").show().addClass("progress active");
                });
            }
            return oFile;
        };
    </script>
</body>
</html>
